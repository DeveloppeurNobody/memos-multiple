package design_patterns.structural.decorator.ftp.component

interface UserPI {
    fun exec(args: MutableList<String> = mutableListOf());
}