pipeline {
    agent any

    stages {
        stage('Test with Keep Last Sources') {
            steps {
                withMaven() {
                    sh 'mvn verify'
                }
            }
        }
        stage('Test Never keep Sources') {
            steps {
                withMaven(options: [coveragePublisher(sourceCodeRetention: "NEVER")]) {
                    sh 'mvn verify'
                }
            }
        }
    }
}
