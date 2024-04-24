<h2>
   🚧🚧🚧 This is a Work in progress <img src="https://geps.dev/progress/40"/> 🚧🚧🚧
</h2>

<h1 align="center">
    Bilubot
</h1>
<p align="center">
	<img src="https://img.shields.io/badge/java-21.0.2-blue?style=flat&color=0080ff" alt="java-version">
	<img src="https://img.shields.io/github/license/lucasaxm/bilubot-java?style=flat&color=0080ff" alt="license">
	<img src="https://img.shields.io/github/last-commit/lucasaxm/bilubot-java?style=flat&logo=git&logoColor=white&color=0080ff" alt="last-commit">
<p>
<p align="center">
		<em>Developed with the software and tools below.</em>
</p>
<p align="center">
	<img src="https://img.shields.io/badge/Telegram-26A5E4.svg?style=flat&logo=Telegram&logoColor=white" alt="Telegram">
	<img src="https://img.shields.io/badge/GitHub%20Actions-2088FF.svg?style=flat&logo=GitHub-Actions&logoColor=white" alt="GitHub%20Actions">
	<img src="https://img.shields.io/badge/Gradle-02303A.svg?style=flat&logo=Gradle&logoColor=white" alt="Gradle">
	<img src="https://img.shields.io/badge/FFmpeg-007808.svg?style=flat&logo=FFmpeg&logoColor=white" alt="FFmpeg">
</p>
<hr>

##  Quick Links

> - [ Overview](#-overview)
> - [ Dependencies](#-dependencies)
> - [ Features](#-features)
> - [ Modules](#-modules)
> - [ Getting Started](#-getting-started)
>   - [ Installation](#-installation)
>   - [ Usage](#-usage)
>   - [ Configuration](#-configuration)
> - [ Contributing](#-contributing)

---

##  Overview

Bilubot-java is an open-source software project configuring a Telegram bot using a Java-based Spring Boot framework. It offers an interactive service on the Telegram platform, leveraging media handling capabilities. The bot automates tasks and responds to users, facilitated by a CI/CD pipeline that ensures streamlined development and deployment, simplifying management and promoting consistent performance. The codebase provides robustness with testing and build automation.

---

##  Dependencies

- Java 21
- gallery-dl
- FFprobe

##  Features

|    | Feature           | Description |
|----|-------------------|---------------------------------------------------------------|
| ⚙️  | **Architecture**  | Built with a Java-based Spring Boot framework, focused on creating a Telegram bot service with media processing capabilities. Modular in design. |
| 🔩 | **Code Quality**  | Code style follows typical Java conventions. Utilizes Gradle for build automation, which implies a standardized project structure. |
| 🔌 | **Integrations**  | Includes Telegram bot integration. Media processing capabilities imply integration with video/audio processing tools like `ffmpeg`. |
| 🧩 | **Modularity**    | Uses Spring Boot's modularity with clear separation of config files for different environments (dev, prod, stage). |
| 🛡️ | **Security**      | Production config suggests encrypted credentials, hinting at usage of secure methods for sensitive information handling. |
| 📦 | **Dependencies**  | Dependencies include Spring Boot starters, Telegram bot library, Jackson for JSON, `ffmpeg`, Apache Tika, and Gradle build tools. |

---

##  Modules

<details closed><summary>.</summary>

| File                                                                                    | Summary                                                                                                                                                                                                                                                                                               |
| ---                                                                                     | ---                                                                                                                                                                                                                                                                                                   |
| [settings.gradle](https://github.com/lucasaxm/bilubot-java/blob/master/settings.gradle) | The settings.gradle file names the root project bilubot, establishing the project identity within the build system of its Java-based repository.                                                                                                                                                      |
| [build.gradle](https://github.com/lucasaxm/bilubot-java/blob/master/build.gradle)       | This `build.gradle` file defines the build configuration for a Java-based Spring Boot application, setting up necessary dependencies for web service, actuator, data binding, a Telegram bot, encryption, media downloading, and processing, as well as testing frameworks and annotation processing. |
| [gradlew.bat](https://github.com/lucasaxm/bilubot-java/blob/master/gradlew.bat)         | This is a Windows batch script that facilitates the Gradle wrapper usage in the bilubot-java project, enabling build automation and dependency management.                                                                                                                                            |

</details>

<details closed><summary>.github.workflows</summary>

| File                                                                                                                                      | Summary                                                                                                                                                                |
| ---                                                                                                                                       | ---                                                                                                                                                                    |
| [build-and-test-pull-request.yml](https://github.com/lucasaxm/bilubot-java/blob/master/.github/workflows/build-and-test-pull-request.yml) | This YAML file automates build and test processes for PRs within a Java-based project, ensuring code integrity before integration.                                     |
| [release-and-deploy.yml](https://github.com/lucasaxm/bilubot-java/blob/master/.github/workflows/release-and-deploy.yml)                   | This YAML configures automated release processes and deployment workflows, ensuring consistent and efficient software delivery within the repository's CI/CD pipeline. |

</details>

<details closed><summary>src.main.resources</summary>

| File                                                                                                                   | Summary                                                                                                                                                                                                                                                                             |
| ---                                                                                                                    | ---                                                                                                                                                                                                                                                                                 |
| [application-prod.yml](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/resources/application-prod.yml)   | The `application-prod.yml` sets encrypted production credentials for Telegram bots, essential for the bot's operation and secure communication within the application's deployment workflow.                                                                                        |
| [application-dev.yml](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/resources/application-dev.yml)     | This `application-dev.yml` configures development environment settings, specifying the server port and encrypted Telegram bot credentials within a Java-based bot application.                                                                                                      |
| [application-stage.yml](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/resources/application-stage.yml) | The `application-stage.yml` configures stage environment variables for the Telegram bots within the Java-based bilubot project, enabling secure communication and control.                                                                                                          |
| [application.yml](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/resources/application.yml)             | The code belongs to a Java-based bot application called bilubot-java, with CI/CD workflows for testing and deployment, structured with Gradle build automation. Its architecture comprises multiple Java packages and is configured to accommodate development-specific properties. |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot</summary>

| File                                                                                                                                       | Summary                                                                                                                                                         |
| ---                                                                                                                                        | ---                                                                                                                                                             |
| [BilubotApplication.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/BilubotApplication.java) | The `BilubotApplication` initializes and runs a Spring Boot application, registers Telegram bot functionality and persists configuration for gallery downloads. |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.configuration</summary>

| File                                                                                                                                                                     | Summary                                                                                                                                                                                                            |
| ---                                                                                                                                                                      | ---                                                                                                                                                                                                                |
| [TelegramBotHealthIndicator.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/configuration/TelegramBotHealthIndicator.java) | Acts as a Spring Boot health indicator, reporting status and details of a Telegram bot within a Java-based service monitoring system.                                                                              |
| [BeansConfig.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/configuration/BeansConfig.java)                               | The `BeansConfig.java` establishes bean definitions for integration of GalleryDl within a Spring Boot application. This configuration is pivotal for the dependency injection of GalleryDl throughout the service. |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.util</summary>

| File                                                                                                                                    | Summary                                                                                                                                                                                                    |
| ---                                                                                                                                     | ---                                                                                                                                                                                                        |
| [InputMediaType.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/util/InputMediaType.java) | The snippet defines media types handled by the Bilubot app within its utility package, contributing to content processing logic.                                                                           |
| [TelegramUtils.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/util/TelegramUtils.java)   | The `TelegramUtils.java` acts as a utility class within the bilubot-java project, providing methods to facilitate communication with Telegram's bot API, such as sending animations, audio, and documents. |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.flows</summary>

| File                                                                                                                                                         | Summary                                                                                                                                                                         |
| ---                                                                                                                                                          | ---                                                                                                                                                                             |
| [WorkflowStepRegistration.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/WorkflowStepRegistration.java) | Defines a custom Spring component annotation for registering workflow steps in the bilubot-java service architecture.                                                           |
| [WorkflowAction.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/WorkflowAction.java)                     | The `WorkflowAction` enum in the `bilubot-java` repository defines a range of actions for workflow control in a bot application, categorizing and handling media-related tasks. |
| [WorkflowStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/WorkflowStep.java)                         | This code defines an interface for steps within workflows, crucial for the modular processing logic in a Java-based bot's architecture.                                         |
| [WorkflowManager.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/WorkflowManager.java)                   | The WorkflowManager class orchestrates workflow steps, mapping actions to corresponding steps, ensuring unique registrations within a Java/Spring codebase.                     |
| [WorkflowDataBag.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/WorkflowDataBag.java)                   | The `WorkflowDataBag` serves as a flexible data store within an automation framework, managing state throughout workflow execution in the repository's Java-based architecture. |
| [WorkflowDataKey.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/WorkflowDataKey.java)                   | The `WorkflowDataKey` enum centralizes key identifiers for workflow data exchange within the `bilubot-java` automation bot system.                                              |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.flows.gallerydl</summary>

| File                                                                                                                                                                       | Summary                                                                                                                                                                                                                               |
| ---                                                                                                                                                                        | ---                                                                                                                                                                                                                                   |
| [GetNextMetadataStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/gallerydl/GetNextMetadataStep.java)               | This component iterates through gallery metadata in a workflow, preparing for media upload to Telegram, and transitions to the next step.                                                                                             |
| [DownloadStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/gallerydl/DownloadStep.java)                             | This code defines a workflow step for the bilubot-java bot, handling the download of gallery images via the gallery-dl library, integrating with Telegram updates.                                                                    |
| [SendMediaGroupStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/gallerydl/SendMediaGroupStep.java)                 | The SendMediaGroupStep class handles sending media groups to Telegram, including conditionally getting more metadata if needed.                                                                                                       |
| [UploadAndAddInputMediaStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/gallerydl/UploadAndAddInputMediaStep.java) | This code snippet defines a workflow step for BiluBot that uploads media and updates the workflow data with the media type.                                                                                                           |
| [GetNextResultStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/gallerydl/GetNextResultStep.java)                   | This code defines a step in a workflow for handling gallery download results. It retrieves the next result from a list and updates the workflow's state, transitioning to metadata retrieval or terminating if no more results exist. |
| [IdentifyCategoryStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/gallerydl/IdentifyCategoryStep.java)             | This code defines a workflow step for a Java-based bot, categorizing downloaded media based on metadata.                                                                                                                              |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.flows.gallerydl.categories</summary>

| File                                                                                                                                                                    | Summary                                                                                                                                                               |
| ---                                                                                                                                                                     | ---                                                                                                                                                                   |
| [RedditCategoryStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/gallerydl/categories/RedditCategoryStep.java)   | This code defines a workflow step for processing Reddit media in a bot, adding subreddit and author metadata as captions.                                             |
| [TwitterCategoryStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/gallerydl/categories/TwitterCategoryStep.java) | This component enriches media with Twitter metadata before sending, integrating with a workflow system within a Java-based bot architecture.                          |
| [GenericCategoryStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/gallerydl/categories/GenericCategoryStep.java) | This code defines a workflow step that updates the caption of the last media item in a Telegram media group with its gallery metadata category, then logs the update. |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.flows.ping</summary>

| File                                                                                                                                                      | Summary                                                                                                                                       |
| ---                                                                                                                                                       | ---                                                                                                                                           |
| [BuildPongMessageStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/ping/BuildPongMessageStep.java) | The BuildPongMessageStep class in bilubot-java repository constructs a response with latency for a Telegram bot using the workflow framework. |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.flows.common</summary>

| File                                                                                                                                              | Summary                                                                                                                                                    |
| ---                                                                                                                                               | ---                                                                                                                                                        |
| [SendMessageStep.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/flows/common/SendMessageStep.java) | The SendMessageStep.java integrates into a Java-based bot framework, executing the action of sending messages via Telegram's API and logging the response. |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.models.gallerydl</summary>

| File                                                                                                                                                          | Summary                                                                                                                                                                                 |
| ---                                                                                                                                                           | ---                                                                                                                                                                                     |
| [Downloader.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/models/gallerydl/Downloader.java)                   | The `Downloader` class in the `bilubot-java` repo is part of the `gallerydl` model layer, managing download configurations including options for a YouTube downloader.                  |
| [GalleryDlProperties.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/models/gallerydl/GalleryDlProperties.java) | This code defines configuration properties for GalleryDL within the BiluBot application, encapsulating path settings and GalleryDL-specific config values.                              |
| [GalleryDlConfig.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/models/gallerydl/GalleryDlConfig.java)         | The `GalleryDlConfig.java` defines a configuration model for gallery downloads within the bilubot-java repo, specifically detailing extractor and downloader settings.                  |
| [Extractor.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/models/gallerydl/Extractor.java)                     | The `Extractor.java` defines a model consolidating various social media content extractors within the larger `bilubot-java` application, laying groundwork for media download features. |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.models.gallerydl.extractors</summary>

| File                                                                                                                                             | Summary                                                                                                                                                                            |
| ---                                                                                                                                              | ---                                                                                                                                                                                |
| [Reddit.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/models/gallerydl/extractors/Reddit.java)   | This code defines a Reddit extractor model within a Java-based bot platform, encapsulating credentials and configuration for Reddit API interactions.                              |
| [Ytdl.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/models/gallerydl/extractors/Ytdl.java)       | This code defines a configurable `Ytdl` class for handling YouTube downloader operations, integrated within a Java project that includes automatic build and deployment processes. |
| [Twitter.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/models/gallerydl/extractors/Twitter.java) | This class defines a Twitter extractor model in `bilubot-java`, responsible for configuring tweet retrievals such as texts, quotes, and retweets.                                  |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.models.gallerydl.extractors.ytdl</summary>

| File                                                                                                                                                    | Summary                                                                                                                                         |
| ---                                                                                                                                                     | ---                                                                                                                                             |
| [Steam.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/models/gallerydl/extractors/ytdl/Steam.java)       | The `Steam` class in `bilubot-java` defines a data model for media filenames, utilized by the gallery download feature's YouTube-DL extractors. |
| [Facebook.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/models/gallerydl/extractors/ytdl/Facebook.java) | The `Facebook.java` class defines a data model for Facebook media extraction, supporting the repository's video download feature set.           |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.models.gallerydl.downloaders</summary>

| File                                                                                                                                        | Summary                                                                                                                                                          |
| ---                                                                                                                                         | ---                                                                                                                                                              |
| [Ytdl.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/models/gallerydl/downloaders/Ytdl.java) | The Ytdl class, a model in the bilubot's gallerydl package, represents a downloader, encapsulating command-line arguments and module data for YouTube downloads. |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.routes</summary>

| File                                                                                                                                      | Summary                                                                                                                                                                                                                                                                              |
| ---                                                                                                                                       | ---                                                                                                                                                                                                                                                                                  |
| [GalleryDlRoute.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/routes/GalleryDlRoute.java) | The `GalleryDlRoute` class integrates with a Telegram bot, deciding if incoming messages containing URLs trigger a download action within the bot's workflow framework. It selectively activates the download workflow based on message content within a Java-based bot application. |
| [Route.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/routes/Route.java)                   | The Route.java interface is a part of the bilubot's core routing system, designed to handle Telegram bot updates and determine corresponding workflow actions within the bot's Java-based architecture.                                                                              |
| [PingRoute.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/routes/PingRoute.java)           | The `PingRoute` class defines a command handler within a Java-based Telegram bot framework, responding to `/ping` commands with a pong message.                                                                                                                                      |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.bots</summary>

| File                                                                                                                      | Summary                                                                                                                                      |
| ---                                                                                                                       | ---                                                                                                                                          |
| [BiluBot.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/bots/BiluBot.java) | The `BiluBot.java` integrates Telegram API, routing messages to services within the Bilubot Java backend architecture, handling bot updates. |

</details>

<details closed><summary>src.main.java.com.boatarde.bilubot.service</summary>

| File                                                                                                                                     | Summary                                                                                                                                                      |
| ---                                                                                                                                      | ---                                                                                                                                                          |
| [RouterService.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/main/java/com/boatarde/bilubot/service/RouterService.java) | The RouterService routes incoming Telegram updates to specific workflows, managing the execution of actions within a defined path in the bot's architecture. |

</details>

<details closed><summary>src.test.java.com.boatarde.bilubot</summary>

| File                                                                                                                                                 | Summary                                                                                                                                                 |
| ---                                                                                                                                                  | ---                                                                                                                                                     |
| [BilubotApplicationTests.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/BilubotApplicationTests.java) | The snippet is a placeholder for Bilubot's integration tests, verifying the Spring Boot application context loads correctly within the Java repository. |

</details>

<details closed><summary>src.test.java.com.boatarde.bilubot.flows.gallerydl</summary>

| File                                                                                                                                                                               | Summary                                                                                                                                                                                                                        |
| ---                                                                                                                                                                                | ---                                                                                                                                                                                                                            |
| [SendMediaGroupStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/gallerydl/SendMediaGroupStepTest.java)                 | This test validates the SendMediaGroupStep within BiluBot's gallery download workflow, ensuring multi-media messages function correctly.                                                                                       |
| [DownloadStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/gallerydl/DownloadStepTest.java)                             | This snippet is for a test class in a Java-based bot (`BiluBot`) project focused on verifying the download functionality within a `gallerydl` flow, crucial for ensuring module integrity within the overall bot architecture. |
| [GetNextMetadataStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/gallerydl/GetNextMetadataStepTest.java)               | This Java test class evaluates the functionality of the GetNextMetadataStep in the gallery download workflow within the bilubot application. It ensures integrity in image metadata processing for the Telegram bot service.   |
| [IdentifyCategoryStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/gallerydl/IdentifyCategoryStepTest.java)             | This test suite verifies the IdentifyCategoryStep functionality within the gallery download workflow, ensuring accurate metadata category identification for Twitter, Reddit, and unspecified sources.                         |
| [UploadAndAddInputMediaStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/gallerydl/UploadAndAddInputMediaStepTest.java) | The code is a test file validating `UploadAndAddInputMediaStep` in a Java bot application, ensuring workflow integrity for gallery downloads and media processing.                                                             |
| [GetNextResultStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/gallerydl/GetNextResultStepTest.java)                   | Unit test for GalleryDL flow, validating the retrieval of subsequent results within the application's testing suite. Helps ensure the reliability of the bot's media gallery download feature.                                 |

</details>

<details closed><summary>src.test.java.com.boatarde.bilubot.flows.gallerydl.categories</summary>

| File                                                                                                                                                                            | Summary                                                                                                                                                                                            |
| ---                                                                                                                                                                             | ---                                                                                                                                                                                                |
| [TwitterCategoryStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/gallerydl/categories/TwitterCategoryStepTest.java) | The code tests a component encapsulating the logic to process Twitter media content, asserting correct workflow transitions and caption formatting within a bot application.                       |
| [RedditCategoryStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/gallerydl/categories/RedditCategoryStepTest.java)   | Tests gallery download workflow's Reddit category handling, confirming metadata and media group captioning in bot's codebase.                                                                      |
| [GenericCategoryStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/gallerydl/categories/GenericCategoryStepTest.java) | This code is part of the test suite for the `bilubot-java` repository, ensuring the `GenericCategoryStep` in the Gallery DL workflow correctly handles media groups with or without subcategories. |

</details>

<details closed><summary>src.test.java.com.boatarde.bilubot.flows.ping</summary>

| File                                                                                                                                                                              | Summary                                                                                                                                                                  |
| ---                                                                                                                                                                               | ---                                                                                                                                                                      |
| [BuildPongMessageWorkflowStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/ping/BuildPongMessageWorkflowStepTest.java) | This test validates the BuildPongMessageWorkflowStep, ensuring the pong reply is correctly created within BiluBot's messaging flow without bot interaction side-effects. |

</details>

<details closed><summary>src.test.java.com.boatarde.bilubot.flows.common</summary>

| File                                                                                                                                                      | Summary                                                                                                                                                                     |
| ---                                                                                                                                                       | ---                                                                                                                                                                         |
| [SendMessageStepTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/flows/common/SendMessageStepTest.java) | The code is a test class for validating message sending in a Java bot application, ensuring proper integration and behavior of messaging functionality within the workflow. |

</details>

<details closed><summary>src.test.java.com.boatarde.bilubot.routes</summary>

| File                                                                                                                                    | Summary                                                                                                                                            |
| ---                                                                                                                                     | ---                                                                                                                                                |
| [PingRouteTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/routes/PingRouteTest.java) | The `PingRouteTest.java` tests routing logic within the Telegram bot, verifying correct mapping of `/ping` commands to a specific workflow action. |

</details>

<details closed><summary>src.test.java.com.boatarde.bilubot.factory</summary>

| File                                                                                                                                                   | Summary                                                                                                                                                                                                                                                |
| ---                                                                                                                                                    | ---                                                                                                                                                                                                                                                    |
| [TelegramTestFactory.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/factory/TelegramTestFactory.java)   | This code is a test data factory for simulating Telegram bot interactions within the bilubot-java project. It generates mock Telegram `Update` and `Message` objects for unit tests, supporting different message types like text, commands, and URLs. |
| [GalleryDlTestFactory.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/factory/GalleryDlTestFactory.java) | The `GalleryDlTestFactory` class provides utility methods for generating default configuration options and mock results for testing the gallery download functionality within the bilubot-java repository's testing suite.                             |

</details>

<details closed><summary>src.test.java.com.boatarde.bilubot.service</summary>

| File                                                                                                                                             | Summary                                                                                                                                       |
| ---                                                                                                                                              | ---                                                                                                                                           |
| [RouterServiceTest.java](https://github.com/lucasaxm/bilubot-java/blob/master/src/test/java/com/boatarde/bilubot/service/RouterServiceTest.java) | This snippet is for unit tests of `RouterService` within a Java-based bot framework, ensuring routing logic aligns with predefined workflows. |

</details>

---

##  Getting Started

### Installation

1. Clone the repository
   ```
   git clone https://github.com/lucasaxm/bilubot-java.git
   ```

2. Navigate to the project directory
   ```
   cd bilubot-java
   ```

3. Build the project
   ```
   ./gradlew build
   ```

### Usage

Explain how to use the application with examples.

```
./gradlew bootRun
```

The application will start running at `http://localhost:8080`.

### Configuration

The application requires the following environment variables to be set:

- `GALLERYDL_BINARY_PATH`: Path to the `gallery-dl` binary.
- `GALLERYDL_CONFIG_PATH`: Path to the `gallery-dl` configuration file.
- `FFPROBE_BINARY_PATH`: Path to the `ffprobe` binary.
- `BILUBOT_ENC_PASSWORD`: Encryption password for the application.

## Contributing

Explain how others can contribute to the project.

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Create a new Pull Request

<details closed>
    <summary>Contributing Guidelines</summary>

1. **Fork the Repository**: Start by forking the project repository to your GitHub account.
2. **Clone Locally**: Clone the forked repository to your local machine using a Git client.
   ```sh
   git clone https://github.com/lucasaxm/bilubot-java
   ```
3. **Create a New Branch**: Always work on a new branch, giving it a descriptive name.
   ```sh
   git checkout -b new-feature-x
   ```
4. **Make Your Changes**: Develop and test your changes locally.
5. **Commit Your Changes**: Commit with a clear message describing your updates.
   ```sh
   git commit -m 'Implemented new feature x.'
   ```
6. **Push to GitHub**: Push the changes to your forked repository.
   ```sh
   git push origin new-feature-x
   ```
7. **Submit a Pull Request**: Create a PR against the original project repository. Clearly describe the changes and their motivations.

Once your PR is reviewed and approved, it will be merged into the main branch.

</details>

