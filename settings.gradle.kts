
rootProject.name = "Anti-Grian-Switch-Reborn"

pluginManagement {
	repositories {
		maven {
			// RetroFuturaGradle
			name = "GTNH Maven"
			url = uri("https://nexus.gtnewhorizons.com/repository/public/")
			mavenContent {
				includeGroupByRegex("com\\.gtnewhorizons\\..+")
				includeGroup("com.gtnewhorizons")
			}
		}
		gradlePluginPortal()
		mavenCentral()
		mavenLocal()
	}
}
