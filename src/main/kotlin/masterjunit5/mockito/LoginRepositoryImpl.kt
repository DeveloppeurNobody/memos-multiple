package masterjunit5.mockito

import java.util.*


class LoginRepositoryImpl : LoginRepository{
    var users: MutableMap<String, String> = HashMap()

    override fun login(userForm: UserForm): Boolean {
        println("LoginRepository.login $userForm")
        val username = userForm.username
        val password = userForm.password
        return (users.keys.contains(username)
                && users[username] == password)
    }

    init {
        // Users of the system are stored in this map (key=username,
        // value=password)
        users["user1"] = "p1"
        users["user2"] = "p3"
        users["user3"] = "p4"
    }
}