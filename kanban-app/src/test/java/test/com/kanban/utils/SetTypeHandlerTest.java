package test.com.kanban.utils;

import com.kanban.utils.SetTypeHandler;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.Collection;
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

    @Test
    public void returnMutableEmptySetIfColumnWasNull() throws Exception {
        context.checking(new Expectations() {{
            oneOf(resultSet).getString(1);
            will(returnValue(null));
        }});

        Set<String> result = typeHandler.getResult(resultSet, 1);
        assertThat(result, empty());

        result.add("one");
        assertThat(result, hasSize(1));
    }

    @Test
    public void returnItemsSeparatedByComma() throws Exception {
        context.checking(new Expectations() {{
            oneOf(resultSet).getString(1);
            will(returnValue("one,two,three"));
        }});

        assertThat((Set<String>) typeHandler.getResult(resultSet, 1), equalTo(asSet("one", "two", "three")));
    }
}