pipeline {
    agent any

    environment {
        MAVEN_HOME = '/usr/share/maven'
        GIT_REPO_URL = 'https://github.com/Kchaow/Diplome_OrderProcessingMicroserviceExample.git'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: "${GIT_REPO_URL}"
            }
        }

        stage('Build with Maven') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                input cancel: 'Нет', message: 'Произвести деплой?', ok: 'Да'
            }
        }
    }
}