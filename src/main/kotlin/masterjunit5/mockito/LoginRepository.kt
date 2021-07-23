package masterjunit5.mockito

interface LoginRepository {
    fun login(userForm: UserForm): Boolean;
}