package test.com.kanban.utils;

import com.kanban.utils.SetTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Set;

import static com.kanban.utils.CollectionUtils.asSet;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class SetTypeHandlerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private SetTypeHandler typeHandler = new SetTypeHandler();
    @Mock
    private ResultSet resultSet;
    @Mock
    private PreparedStatement statement;

    @Test
    public void setParameterAsEmptyStringWithEmptySet() throws Exception {
        assertSetParameter(with(), equalTo(""));
    }

    @Test
    public void setParameterAsEmptyStringWithNull() throws Exception {
        assertSetParameter(null, equalTo(""));
    }

    @Test
    public void setParameterAsItemWithSingleItem() throws Exception {
        assertSetParameter(with("one"), equalTo("one"));
    }

    @Test
    public void setJoinedItemsWithItems() throws Exception {
        assertSetParameter(with("one", "two", "three"), equalTo("one,two,three"));
    }


    @Test
    public void returnMutableEmptySetIfColumnWasNull() throws Exception {
        assertResultInColumnValue(null, emptyCollectionOf(String.class));
    }

    @Test
    public void returnMutableSetContainingItemsSeparatedByComma() throws Exception {
        assertResultInColumnValue("one,two,three", equalTo((Collection<String>) asSet("one", "two", "three")));
    }

    private void assertSetParameter(Set<String> parameter, final Matcher<String> matcher) throws SQLException {
        final int parameterIndex = randomIndex();

        context.checking(new Expectations() {{
            oneOf(statement).setObject(with(parameterIndex), with(matcher), with(Types.VARCHAR));
        }});

        typeHandler.setParameter(statement, parameterIndex, parameter, JdbcType.VARCHAR);
    }

    private int randomIndex() {
        return (int) Math.round(1 + Math.random() * 10);
    }

    private Set<String> with(String... items) {
        return asSet(items);
    }

    private void assertResultInColumnValue(final String value, Matcher<Collection<String>> matcher) throws SQLException {
        final int columnIndex = randomIndex();
        context.checking(new Expectations() {{
            oneOf(resultSet).getString(columnIndex);
            will(returnValue(value));
        }});

        Set<String> result = typeHandler.getResult(resultSet, columnIndex);

        int currentSize = result.size();
        assertThat(result, matcher);
        result.add("<more>");
        assertThat(result, hasSize(currentSize + 1));
    }

}