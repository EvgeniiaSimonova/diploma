import com.app.ui.validators.LoginValidator;
import com.vaadin.data.Validator;

/**
 * @author Evgenia Simonova
 */
public class TestClass {
    public static void main(String[] args) {
        String s = " azAZd_";
        LoginValidator loginValidator = new LoginValidator();
        try {
            loginValidator.validate(s);
        } catch (Validator.InvalidValueException e) {
            System.out.println(e.getMessage());
        }
    }
}
