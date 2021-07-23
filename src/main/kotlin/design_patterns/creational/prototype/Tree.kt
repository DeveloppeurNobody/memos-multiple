package design_patterns.creational.prototype

class User(var name: String = "", var email: String = "")

class UserService {
    val users = mutableListOf<User>();

    init {
        with (users) {
            add(User("Albert", "albert@gmail.com"))
            add(User("Bob", "bob@gmail.com"))
            add(User("Gary", "gary@gmail.com"))
            add(User("Geno", "geno@gmail.com"))
        }
    }
}

class UserDetails(var userList: MutableList<User> = mutableListOf()) : Cloneable {
    fun getUsers() {
        val userService = UserService();
        this.userList = userService.users;
    }

    override fun clone(): Any {
        val temp = mutableListOf<User>();
        userList.forEach {
            temp.add(it);
        }
        return UserDetails(temp);
    }
}