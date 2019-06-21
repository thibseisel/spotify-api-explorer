rootProject.name = "spotify-api-explorer"

pluginManagement {
    repositories {
        if (settings.extra.has("repositoryMirrors")) {
            val repositoryMirrors: String by settings
            repositoryMirrors.split(',').forEach { mirrorUrl ->
                maven { url = uri(mirrorUrl) }
            }
        } else {
            gradlePluginPortal()
        }
    }
}
