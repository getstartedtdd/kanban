package test.com.kanban.utils;

import com.kanban.utils.SetTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Set;

import static com.kanban.utils.CollectionUtils.asSet;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;


public class SetTypeHandlerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private SetTypeHandler typeHandler = new SetTypeHandler();
    @Mock
    private ResultSet resultSet;
    @Mock
    private PreparedStatement statement;
    private final int index = 1;

    private void expectParameterToBeSet(final int columnIndex, final String parameter) throws SQLException {
        context.checking(new Expectations() {{
            oneOf(statement).setObject(columnIndex, parameter, Types.VARCHAR);
        }});
    }

    @Test
    public void setParameterAsEmptyStringWithEmptySet() throws Exception {
        expectParameterToBeSet(index, "");

        typeHandler.setParameter(statement, index, Collections.emptySet(), JdbcType.VARCHAR);
    }

    @Test
    public void setParameterAsEmptyStringWithNull() throws Exception {
        expectParameterToBeSet(index, "");

        typeHandler.setParameter(statement, index, null, JdbcType.VARCHAR);
    }

    @Test
    public void setParameterAsItemWithSingleItem() throws Exception {
        expectParameterToBeSet(index, "one");

        typeHandler.setParameter(statement, index, asSet("one"), JdbcType.VARCHAR);
    }

    @Test
    public void setJoinedItemsWithItems() throws Exception {
        expectParameterToBeSet(index, "one,two,three");

        typeHandler.setParameter(statement, index, asSet("one", "two", "three"), JdbcType.VARCHAR);
    }

    @Test
    public void returnMutableEmptySetIfColumnWasNull() throws Exception {
        expectColumnToBeFilled(index, null);

        Set<String> result = typeHandler.getResult(resultSet, index);
        assertThat(result, empty());

        result.add("one");
        assertThat(result, hasSize(1));
    }

    @Test
    public void returnItemsSeparatedByComma() throws Exception {
        expectColumnToBeFilled(index, "one,two,three");

        assertThat(typeHandler.getResult(resultSet, index), equalTo(asSet("one", "two", "three")));
    }

    private void expectColumnToBeFilled(final int columnIndex, final String result) throws SQLException {
        context.checking(new Expectations() {{
            oneOf(resultSet).getString(columnIndex);
            will(returnValue(result));
        }});
    }
}