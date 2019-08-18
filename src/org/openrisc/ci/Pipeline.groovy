package org.openrisc.ci

class Pipeline {

    def steps

    Pipeline(steps) {
        this.steps = steps
    }

// This method pulls standard dockerimage for openrisc related projects . 
    def dockerPull(){
        sh 'docker pull librecores/librecores-ci-openrisc'
        sh 'docker images'
    }

// This method runs or1k-tests against various enviroment variables
    def dockerRun(TESTING-NAME, JOB, SIM, PIPELINE, EXPECTED_FAILURES, EXTRA_CORE_ARGS ){
        stage("$TESTINGNAME") {
            Environment {
                JOB = "$JOB"
                SIM = "$SIM"
                PIPELINE = "$PIPELINE"
                EXPECTED_FAILURES = "$EXPECTED_FAILURES"
                EXTRA_CORE_ARGS =  "$EXTRA_CORE_ARGS"
            }
            steps {
                dockerrun()
            }
        }
    }

    void dockerrun(){
        sh 'docker run --rm -v $(pwd):/src -e "JOB=$JOB" -e "SIM=$SIM" -e "PIPELINE=$PIPELINE" -e "EXPECTED_FAILURES=$EXPECTED_FAILURES" -e "EXTRA_CORE_ARGS=$EXTRA_CORE_ARGS" librecores/librecores-ci-openrisc /src/.travis/test.sh'    
    }
}