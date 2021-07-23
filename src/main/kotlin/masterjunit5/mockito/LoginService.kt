package masterjunit5.mockito

interface LoginService {
    fun login(userForm: UserForm): Boolean;
    fun logout(userForm: UserForm);
}
