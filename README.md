AVIS SAGRA VIEWER
=================
<!-- TOC -->
* [AVIS SAGRA VIEWER](#avis-sagra-viewer)
  * [What is it](#what-is-it)
  * [How was it implemented](#how-was-it-implemented)
  * [Why this choices](#why-this-choices)
<!-- TOC -->
What is it
-----------
Avis Sagra Viewer is a very small and simple application developed as volunteering work.
The context is the following:
- AVIS is an italian nationwide non-profit association focused on handling blood donations, see [AVIS Website](https://www.avis.it/)
- Many towns have their own section of AVIS, handling the local organization of donation campaigns, getting people to know the association and generally promoting blood donations
- All the associations are also funded in various manner locally (by local governments, local donations and specific initiatives)
- One of these initiatives the local branch I belong to organizes a local feast ("sagra") where all the money gotten from serving food goes to the association, to use in the ways described above
- In this context, we use a free but proprietary software to process orders, get the cash etc
- A new requirement came from the cooks in the kitchen to have a way to monitor near-real time how much of each dish had been ordered
- The proprietary software had no easy way to get this information, the only way was through admin access (which we were not sure to leave available to everybody) and in a rather convoluted way.
- So we decided to develop a small utility that could connect to the shared database (in the production configuration a sqlite file on one of the PCs, but easily swappable in a configuration file)

How was it implemented
-----------------------
The choice went to Kotlin as a language, using [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) as the UI library, [Exposed](https://github.com/JetBrains/Exposed) as the SQL interface.

Why this choices
----------------
There are multiple reasons for this, I will try to list all below:
- I chose Kotlin for three reasons:
  - To learn a new language, that according to many surveys is [among the most loved languages](https://survey.stackoverflow.co/2022#section-most-loved-dreaded-and-wanted-programming-scripting-and-markup-languages).
  - Having worked from many years with Java, taking a JVM language with good interoperability with Java itself gave me assurance that, at worst, I could java write java code if needed
  - Knowledge on the toolchain (Gradle etc) and IDE (IntellJ), due to the Java projects worked in the past

On the other hand [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) was not my first choice. At first I was only focused on making a desktop app, therefore using JavaFX and/or its Kotlin port TornadoFX.
While looking around, I discovered the Compose library and it seemed a better fit:
- It allows to build a phone app if so desired. This was not a concern currently, but it could be of use in the future
- The skillset is more applicable in future projects, so it is not wasted knowledge
- It has a nice API

Exposed on the other hand was chose only on gut feeling, but after using I honestly think it was a good choice, the API is decent and very fluent.
