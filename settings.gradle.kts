pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
        maven("https://mirrors.cloud.tencent.com/nexus/repository/google")
        maven("https://mirrors.cloud.tencent.com/nexus/repository/jcenter")
        maven("https://mirrors.cloud.tencent.com/nexus/gradle-plugin")
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    @Suppress("UnstableApiUsage")
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
        maven("https://mirrors.cloud.tencent.com/nexus/repository/google")
        maven("https://mirrors.cloud.tencent.com/nexus/repository/jcenter")
        maven("https://mirrors.cloud.tencent.com/nexus/gradle-plugin")
        google()
        mavenCentral()
    }
}

rootProject.name = "DynamicUI"
include(":app")
include(":dynamicui")
