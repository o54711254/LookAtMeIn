pipeline {
    agent any
    stages {
        stage('Check Changes') {
            steps {
                script {
                    def frontendChanges = sh(script: "git diff --name-only HEAD HEAD~1 | grep 'B206-frontend-repo/'", returnStdout: true).trim()
                    def backendChanges = sh(script: "git diff --name-only HEAD HEAD~1 | grep 'B206-spring-repo/'", returnStdout: true).trim()
                    def pythonChanges = sh(script: "git diff --name-only HEAD HEAD~1 | grep 'B206-python-repo/'", returnStdout: true).trim()
                    if (frontendChanges) {
                        echo "Frontend changes detected"
                        env.FRONTEND_CHANGED = "true"
                    }
                    if (backendChanges) {
                        echo "Backend changes detected"
                        env.BACKEND_CHANGED = "true"
                    }

                    if(pythonChanges) {
                        echo "Python changes detected"
                        env.PYTHON_CHANGED = "true"
                    }
                }
            }
        }
        stage('Build Frontend') {
            when {
                expression { env.FRONTEND_CHANGED == "true" }
            }
            steps {
                // Frontend 빌드 스크립트
            }
        }
        stage('Build Backend') {
            when {
                expression { env.BACKEND_CHANGED == "true" }
            }
            steps {
                // Backend 빌드 스크립트
            }
        }
    }
}
