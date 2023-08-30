rootProject.name = "com.gevamu.build"

pluginManagement {
    includeBuild("build-logic")
}

include(
    "cordapp-publish-conventions",
    "cordformation-conventions",
    "java-common-conventions",
    "java-library-conventions",
    "kotlin-common-conventions",
    "kotlin-cordapp-conventions",
)
