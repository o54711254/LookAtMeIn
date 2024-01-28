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

                sh "pwd"
            }
        }
        stage('Build Frontend') {
            when {
                expression { env.FRONTEND_CHANGED == "true" }
            }
            steps {
                // Frontend 빌드 스크립트
                echo "Frontend build"
                sh "pwd"
                sh "cd B206-frontend-repo"
                sh "npm install"
                sh "CI=false npm run build"
                sh '''
                    ssh -i ~/.ssh/id_rsa ubuntu@i10b206.p.ssafy.io "rm -rf /home/ubuntu/frontend/build/"
                    ssh -i ~/.ssh/id_rsa ubuntu@i10b206.p.ssafy.io "mkdir /home/ubuntu/frontend/build/"
                    scp -i ~/.ssh/id_rsa -r ./build/ ubuntu@i10b206.p.ssafy.io:/home/ubuntu/frontend/
                    scp -i ~/.ssh/id_rsa ./dockerfile ubuntu@i10b206.p.ssafy.io:/home/ubuntu/frontend/dockerfile
                    scp -i ~/.ssh/id_rsa ./init.sh ubuntu@i10b206.p.ssafy.io:/home/ubuntu/frontend/init.sh
                    scp -i ~/.ssh/id_rsa ./nginx.conf ubuntu@i10b206.p.ssafy.io:/home/ubuntu/frontend/nginx.conf
                    ssh -i ~/.ssh/id_rsa ubuntu@i10b206.p.ssafy.io "cd /home/ubuntu/frontend && sh init.sh"
                '''
            }
        }
        stage('Build Backend') {
            when {
                expression { env.BACKEND_CHANGED == "true" }
            }
            steps {
                // Backend 빌드 스크립트
                echo "Backend build"
                sh "pwd"
                sh "cd B206-spring-repo"
                sh "chmod +s gradlew"
                sh "./gradlew build"
                sh'''
                    ssh -i ~/.ssh/id_rsa ubuntu@i10b206.p.ssafy.io "rm -rf /home/ubuntu/spring && mkdir /home/ubuntu/spring"
                    scp -i ~/.ssh/id_rsa ./build/libs/lam-0.0.1-SNAPSHOT.jar ubuntu@i10b206.p.ssafy.io:/home/ubuntu/spring/lam-0.0.1-SNAPSHOT.jar
                    scp -i ~/.ssh/id_rsa ./dockerfile ubuntu@i10b206.p.ssafy.io:/home/ubuntu/spring/dockerfile
                    scp -i ~/.ssh/id_rsa ./init.sh ubuntu@i10b206.p.ssafy.io:/home/ubuntu/spring/init.sh
                    ssh -i ~/.ssh/id_rsa ubuntu@i10b206.p.ssafy.io "cd /home/ubuntu/spring && sh init.sh"
                '''

            }
        }

        stage('Build Python'){
            when{
                expression { env.PYTHON_CHANGED == "true" }
            }

            steps{
                echo "Python build"
                sh "pwd"
                sh "cd B206-python-repo"
                
            }
        }
    }
}
