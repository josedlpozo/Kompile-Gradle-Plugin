node() {

    stage('Checkout idealista-android') {
	git branch: 'test_pipelines', credentialsId: '824dadc6-17b3-4ed2-bdc0-962192b6c656', url: 'git@github.com:josedlpozo/Kompile-Gradle-Plugin.git'
    }

    stage('Merge design') {
        sh './gradlew build'
	sh 'touch jeje.txt'
    }

    stage('Push') {
	sshagent(['824dadc6-17b3-4ed2-bdc0-962192b6c656']) {
		sh 'git checkout -b merge_design'
                sh 'git add .'
                sh 'git commit -m "Merge design"'
                sh 'git push origin merge_design'
	}
    }
}
