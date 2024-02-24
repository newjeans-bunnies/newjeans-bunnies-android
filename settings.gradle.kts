pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "newjeans-bunnies"

include(
    ":app",
    ":designsystem",
    ":auth",
    ":main",
    ":database",
    ":di",
    ":network",
)
