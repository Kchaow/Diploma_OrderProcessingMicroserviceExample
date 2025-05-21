pipeline {
    agent any

    environment {
        MAVEN_HOME = '/usr/share/maven'
        GIT_REPO_URL = 'https://github.com/Kchaow/Diplome_OrderProcessingMicroserviceExample.git'
        MICROSERVICE_NAME = 'order-processing' // Укажите имя вашего микросервиса
        CHANGE_GRAPH_URL = 'http://localhost:8081/api/v1' // Укажите базовый URL сервиса change-graph
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
                echo "Проверка целостности связей"
                script {
                    def response = httpRequest(
                        url: "${CHANGE_GRAPH_URL}/change-graph/process/microservice/${MICROSERVICE_NAME}",
                        httpMode: 'GET',
                        acceptType: 'APPLICATION_JSON'
                    )

                    def graphIds = new groovy.json.JsonSlurper().parseText(response.content)
                    def graphId = 0
                    if (graphIds.isEmpty()) {
                        def createGraphResponse = httpRequest(
                            url: "${CHANGE_GRAPH_URL}/change-graph",
                            httpMode: 'POST',
                            contentType: 'APPLICATION_JSON',
                            requestBody: "{\"associatedMicroservices\": [\"${MICROSERVICE_NAME}\"]}"
                        )
                    }

                    graphId = new groovy.json.JsonSlurper().parseText(createGraphResponse.content).id

                    sh "${MAVEN_HOME}/bin/mvn letunov:spec-provider-maven-plugin:1.0-SNAPSHOT:verifyMicroservice -DchangeGraphId=${graphId}"

                    def attempt = 0
                    def maxAttempt = 15
                    def graphStatus = "WAIT_FOR_COMMIT"
                    def changeGraph = null
                    while (graphStatus != "DONE") {
                        def getChangeGraphStatusResponse = httpRequest(
                            url: "${CHANGE_GRAPH_URL}/change-graph/${graphId}",
                            httpMode: 'GET',
                            acceptType: 'APPLICATION_JSON'
                        )
                        changeGraph = new groovy.json.JsonSlurper().parseText(response)
                        graphStatus = changeGraph.status
                        sleep(7)
                        attempt++
                        if (attempt >= 15) {

                        }
                    }

                    switch(changeGraph.verificationStatus) {
                        case "ERROR":
                            currentBuild.result = 'FAILURE'
                            error("Граф изменений содержит нарушения целостности")
                            break
                        case "WARNING":
                            echo "Граф изменений содержит замечания"
                            break
                    }

                    sh "${MAVEN_HOME}/bin/mvn letunov:spec-provider-maven-plugin:1.0-SNAPSHOT:updateMicroserviceGraph"
                }
                echo "Выполняется деплой"
            }
        }
    }
}
