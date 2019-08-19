package org.openrisc.ci

class Pipeline {

    def steps

    Pipeline(steps) {
        this.steps = steps
    }

    // This method pulls standard dockerimage for openrisc related projects .
    def dockerPull(){
        steps.sh 'docker pull librecores/librecores-ci-openrisc'
        steps.sh 'docker images'
    }

    def dockerRun( TEST , JOB , SIM,PIPELINE , EXPECTED_FAILURES ) {
      stage(${Test}) {
                    environment {
                        JOB = ${JOB}
                        SIM = ${SIM}
                        PIPELINE = ${PIPELINE}
                        EXPECTED_FAILURES= ${EXPECTED_FAILURES}
                        EXTRA_CORE_ARGS = ${ EXTRA_CORE_ARG}
                    }
                    steps {
                      steps.sh 'docker run --rm -v $(pwd):/src -e "JOB=$JOB" -e "SIM=$SIM" -e "PIPELINE=$PIPELINE" -e "EXPECTED_FAILURES=$EXPECTED_FAILURES" -e "EXTRA_CORE_ARGS=$EXTRA_CORE_ARGS" librecores/librecores-ci-openrisc /src/.travis/test.sh'
                    }
        }
    }
