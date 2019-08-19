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

    def parallelJobs = jobs.collectEntries {
        ["${it.name}": buildStage(openRiscPipeline, it)]
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

            stage("build") {
                script {
                    parallel parallelJobs
                }
            }
        }
    }
}
