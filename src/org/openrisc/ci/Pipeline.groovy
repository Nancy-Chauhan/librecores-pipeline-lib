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

// This method runs or1k-tests against various enviroment variable
}
