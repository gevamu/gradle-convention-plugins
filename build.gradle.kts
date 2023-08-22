plugins {
    `lifecycle-base`
}

listOf("build", "clean", "assemble", "check").forEach { taskName ->
    tasks.named(taskName) {
        subprojects.forEach { subproject ->
            dependsOn(subproject.tasks[taskName])
        }
    }
}
