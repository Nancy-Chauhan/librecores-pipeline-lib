import org.openrisc.ci.Pipeline

def call() {
    def openRiscPipeline = new Pipeline(steps)

    stage("CI environment init") {
        steps {
            script {
                openRiscPipeline.initialize()
            }
        }
    }
}