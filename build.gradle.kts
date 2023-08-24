plugins {
    base
}

tasks {
    listOf(assemble, build, check, clean).forEach { task ->
        task {
            subprojects.forEach { subproject ->
                dependsOn(subproject.tasks[task.name])
            }
        }
    }
}
