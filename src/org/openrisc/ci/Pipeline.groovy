package org.openrisc.ci

class Pipeline {
    static final DOCKER_IMAGE = 'librecores/librecores-ci-openrisc'
    static final COMMAND = "/src/.travis/test.sh"


    def steps

    Pipeline(steps) {
        this.steps = steps
    }

    // This method pulls standard dockerimage for openrisc related projects .
    def initialize() {
        steps.sh 'docker pull librecores/librecores-ci-openrisc'
        steps.sh 'docker images'
    }

    def runJob(job, sim = '', pipeline = '', expectedFailures = '', extraCoreArgs = '') {

        def envVars = "-e \"JOB=${job}\" " +
                "-e \"SIM=${sim}\" " +
                "-e \"PIPELINE=${pipeline}\"" +
                " -e \"EXPECTED_FAILURES=${expectedFailures}\" " +
                "-e \"EXTRA_CORE_ARGS=${extraCoreArgs}\""

        steps.sh "docker run --rm -v \$(pwd):/src ${envVars} ${DOCKER_IMAGE} ${COMMAND}"
    }
}
