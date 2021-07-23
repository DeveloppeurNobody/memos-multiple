package masterjunit5.mockito


interface LoginController {
    fun login(userForm: UserForm): String;
    fun logout(userForm: UserForm);
}