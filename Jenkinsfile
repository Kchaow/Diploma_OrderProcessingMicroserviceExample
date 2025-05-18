{
    agent any

    environment {
        GIT_REPO_URL = 'https://github.com/Kchaow/Diplome_OrderProcessingMicroserviceExample.git'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git "${GIT_REPO_URL}"
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}