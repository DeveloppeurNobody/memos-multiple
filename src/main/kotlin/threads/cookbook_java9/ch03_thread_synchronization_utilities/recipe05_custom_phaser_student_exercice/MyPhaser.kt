package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe05_custom_phaser_student_exercice

import java.util.concurrent.Phaser

/**
 * Implements a subclass of the Phaser class. Overrides the onAdvance method to control
 * the change of phase
 */
class MyPhaser : Phaser() {

    /**
     * This method is called when the register thread calls one of the advance methods
     * in the actual phase
     *
     * @param phase actual phase
     * @param registeredParties Number of registred threads
     * @return false to advance the phase, true to finish
     */
    override fun onAdvance(phase: Int, registeredParties: Int): Boolean {
        when(phase) {
            0 -> {
                return studentsArrived();
            }
            1 -> {
                return finishFirstExercise();
            }
            3 -> {
                return finishExam();
            }
            else -> {
                return true;
            }
        }
    }

    /**
     * this method is called in the change from phase 0 to phase 1
     * @return false to continue with the execution
     */
    private fun studentsArrived(): Boolean {
        println("""
            Phaser: The exam are groing to start. The students are ready.
            Phaser: we have $registeredParties students.
        """.trimIndent());

        return false;
    }

    /**
     * This method is called in the change from phase 1 to phase 2
     * @return false to continue with the execution
     */
    private fun finishFirstExercise(): Boolean {
        println("""
            Phaser: All the students has finished the first exercice.
            Phaser: It's turn for the second one.
        """.trimIndent());

        return false;
    }

    /**
     * this method is called in the change from phase 3 to phase 4
     * @return true. There are no more phases
     */
    private fun finishExam(): Boolean {
        println("""
            Phaser: All the students has finish the exam.
            Phaser: Thank you for your time.
        """.trimIndent());

        return true;
    }


}