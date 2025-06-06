# Development ==========================================================================================================
run_install:
    mvn install -DskipTests

run_tests:
    mvn clean install

# Release ==============================================================================================================
run_deploy_snapshot:
    mvn -B -Prelease -DskipTests clean deploy

start_release:
    git remote set-url origin git@github.com:mogami-tech/x402-commons.git
    git checkout development
    git pull
    git status
    mvn gitflow:release-start

finish_release:
    mvn gitflow:release-finish -DskipTests

run_deploy_release:
    mvn -B -Prelease -DskipTests clean deploy
