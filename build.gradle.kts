plugins {
	id("com.gtnewhorizons.retrofuturagradle") version "1.4.5"
	id("io.freefair.lombok") version "8.13"
}

group = "dev.gamelord2011"
version = "1.12-1.0.0"

val id = "ags_reborn"

minecraft {
	mcVersion = "1.12.2"
	extraRunJvmArguments = listOf("-Dforge.logging.console.level=debug")
}

repositories {
	maven {
		name = "Cleanroom"
		url = uri("https://maven.cleanroommc.com")
	}

	maven {
		name = "CoFH Maven"
		url = uri("https://maven.covers1624.net")
	}
}

dependencies {
	compileOnly(rfg.deobf("mezz:jei:4.26.2"))
}

// Set the toolchain version to decouple the Java we run Gradle with from the Java used to compile and run the mod
java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))

tasks {

	processResources {
		val expandProperties = mapOf(
			"version" to project.version,
			"name" to project.name,
			"id" to id
		)

		inputs.properties(expandProperties)

		filesMatching("**/*.*") {
			if (!path.endsWith(".png")) {
				if (path.endsWith("mixins.alfheim.json")) {
					filter { line ->
						expandProperties.entries.fold(line) { acc, (key, value) ->
							acc.replace("\${$key}", value.toString())
						}
					}
				} else {
					expand(expandProperties)
				}
			}
		}
	}

	withType<Jar> {
		manifest {
			attributes(
				"ModSide" to "BOTH",
				"FMLAT" to "top_at.cfg",
			)
		}
	}

	withType<JavaCompile> {
		options.encoding = "UTF-8"

		options.isFork = true
		options.forkOptions.jvmArgs = listOf("-Xmx4G", "-XX:+UseStringDeduplication")
	}
}