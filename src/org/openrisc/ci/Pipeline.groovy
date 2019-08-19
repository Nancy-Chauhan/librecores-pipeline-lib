package org.openrisc.ci

class Pipeline {

    def steps

    Pipeline(steps) {
        this.steps = steps
    }

    // This method pulls standard dockerimage for openrisc related projects .
    def dockerPull() {
        steps.sh 'docker pull librecores/librecores-ci-openrisc'
        steps.sh 'docker images'
    }

    def dockerRun(test, job, sim = '', pipeline = '', expectedFailures = '', extraCoreArgs = '') {
        stage(test) {
//            environment {
//                JOB = job
//                SIM = sim
//                PIPELINE = pipeline
//                EXPECTED_FAILURES = expectedFailures
//                EXTRA_CORE_ARGS = extraCoreArgs
//            }
            envVars = "-e \"JOB=${job}\" " +
                    "-e \"SIM=${sim}\" " +
                    "-e \"PIPELINE=${pipeline}\"" +
                    " -e \"EXPECTED_FAILURES=${expectedFailures}\" " +
                    "-e \"EXTRA_CORE_ARGS=${extraCoreArgs}\""
            image = 'librecores/librecores-ci-openrisc'
            def command = "/src/.travis/test.sh"
            steps {
                steps.sh "docker run --rm -v \$(pwd):/src ${envVars} ${image} ${command}"
            }
        }
    }
}
