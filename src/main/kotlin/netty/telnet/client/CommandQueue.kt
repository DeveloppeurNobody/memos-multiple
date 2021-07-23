package netty.telnet.client

/*

class CommandQueue {
    val LOG = GenerateLogger.getLoggerForClass(this);

    val cmdFactory = CmdFactory();
    var isAvailable = true;

    init {
        LOG.info("CommandQueue+++++++++++++++++++++++++++");
    }

    var queueOfUserPICommands: Queue<UserPI> = LinkedList();

    fun launchNextCommand(commandQueueInfos: CommandQueueInfos) {
        LOG.info("=======================>>>> launchNextCommand() #commandQueueInfos: $commandQueueInfos");

        if (commandQueueInfos.isDone) {
            isAvailable = true;
            LOG.info("=======================>>>> isDone #it: ${commandQueueInfos.userPICommand.name}");
            val currentUserPIName = commandQueueInfos.userPICommand.name;
            if (currentUserPIName.equals(CommandConstantes.USER) || currentUserPIName.equals(CommandConstantes.PASS)) {
                LOG.info("currentUserPIName: $currentUserPIName")
            } else {
                if (queueOfUserPICommands.isNotEmpty()) {
                    runCommand();
                } else {
                    LOG.info(">>>> no command remaining in queue");
                }
            }
        }

    }

    fun addCommandToQueue(commandBody: PresentationModel.CommandBody) {
        LOG.info("addCommandToQueue() #commandBody: $commandBody");
        // instance of cmd factory

        // getting service by factory
        val currentCmd = cmdFactory.getCommand(commandBody.name);
        commandBody.args.forEach {
            LOG.info("++++++ $it")
        }
//        // exec command with args of commandBody;
        currentCmd.exec(commandBody.args.toMutableList());

        queueOfUserPICommands.add(currentCmd);

        runCommand();
    }

    private fun runCommand() {
        if (isAvailable) {
            LOG.info("runCommand() #isAvailable is true");
            executeCommand();
        } else {
            LOG.info("runCommand() #isAvailable is false");
        }
    }

    private fun executeCommand() {
        LOG.info("executeCommand()");
        if (queueOfUserPICommands.isEmpty()) {
            LOG.info("executeCommand() #queue is empty");
        } else {
            val userPICommand = queueOfUserPICommands.poll();
            LOG.info("executeCommand() #userPICommand: ${userPICommand.name}");
            userPICommand.exec();
        }
    }

}

data class CommandQueueInfos(
    var userPICommand: UserPI = BaseUserPI(),
    var isDone: Boolean = true
)

*/