pipeline{
    agent any
    options {
        timestamps()
    }
    tools { 
      maven 'MAVEN-3.8.4' 
      jdk 'JAVA-1.8' 
    }
    stages{
        stage('Git clone'){
            steps{
                git branch: '$Branch', url: 'https://github.com/argbwl/account-validation.git'
            }
        }
        stage('MVN clean install'){
            steps{
               sh 'mvn clean'
            }
        }
        stage('MVN Build'){
            steps{
                sh 'mvn package'
            }
        }
        stage('MVN Test'){
            steps{
                sh 'mvn test'
            }
        }
        stage('Deplyment'){
            steps{
                echo "Deplyment done"
            }
        }
    }
}
