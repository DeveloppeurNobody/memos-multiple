package masterjunit5.mockito

class LoginServiceImpl(var loginRepository: LoginRepository = LoginRepositoryImpl()) : LoginService {

    private var usersLogged: MutableList<String> = mutableListOf();

    override fun login(userForm: UserForm): Boolean {
        println("LoginService.login $userForm");

        // preconditions
        checkForm(userForm);

        // Same user cannot be logged twice
        var username: String = userForm.username;
        if (usersLogged.contains(username)) {
            throw LoginException("$username already logged");
        }

        // call repository to make magic
        var login = loginRepository.login(userForm);
        if (login) {
            usersLogged.add(username);
        }
        return login;
    }

    override fun logout(userForm: UserForm) {
        println("LoginService.logout $userForm");

        // preconditions
        checkForm(userForm);

        // Same user cannot be logged twice
        var username: String = userForm.username;
        if (!usersLogged.contains(username)) {
            throw LoginException("$username not logged");
        }

        usersLogged.remove(username);
    }

    fun getUserLoggedCount(): Int {
        return usersLogged.size;
    }

    private fun checkForm(userForm: UserForm) {
        assert(userForm != null);
        assert(userForm.username != null);
        assert(userForm.password != null);
    }
}


