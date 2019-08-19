import org.openrisc.ci.Pipeline

def buildStage(openRiscPipeline, job) {
    return {
        stage("${job.get('name')}") {
            steps {
                script {
                    openRiscPipeline.dockerRun(
                            job.get('name'),
                            job.get('sim', ''),
                            job.get('pipeline', ''),
                            job.get('expectedFailures', ''),
                            job.get('extraCoreArgs', '')
                    )
                }
            }
        }
    }
}

def call(jobs) {
    def openRiscPipeline = new Pipeline(steps)

    parallelJobs = jobs.collectEntries {
        [ "${it.name}": buildStage(openRiscPipeline, it)]
    }

    pipeline {
        agent any
        stages {
            stage("pre-build") {
                steps {
                    script {
                        openRiscPipeline.initialize()
                    }
                }
            }
        }

        stage("build") {
            parallel {
                stage("verilator") {
                    steps {
                        script {
                            x.dockerRun('verilator')
                        }
                    }
                }
                stage("testing 1") {
                    steps {
                        script {
                            x.dockerRun('or1k-tests', 'icarus', 'CAPPUCCINO', 'or1k-cy')
                        }
                    }
                }
            }
        }
    }
}
