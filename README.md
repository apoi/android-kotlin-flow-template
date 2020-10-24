# Android Kotlin Flow Architecture Template

This project implements a simple Kotlin Flow based Android app. Goal of this project is to serve as
a template for Flow based apps and as a playground for testing Android architectural approaches.

## Project features

* All Kotlin with Gradle Kotlin DSL
* Single Activity with AndroidX navigation
* Kotlin Flow based MVVM architecture
* Repository pattern
    * Data validation for choosing between local and remote data
    * Local data persisting with Stores
        * Store/Core separation for switching between persistence methods
        * Implementation for in-memory non-persisting store
        * Implementation for Room based persisting store
        * Implementation for a caching store combining both in-memory and Room
            * Combines persistence with performance of in-memory storage
    * Remote data layer with suspending functions
        * Success/Error result Retrofit delegate
        * Fetchers for request deduplication
* View Binding
    * Convenience methods for creating bindings for Activities and Fragments
* Single RecyclerView adapter
    * TypeFactory approach for avoiding need for separate adapters
    * Simple ViewHolders and lazy ViewHolders with async data loading
    * View Binding for ViewHolders through TypeFactory
    * TouchListener approach for click event handling
* Koin for dependency injection

## License

    Copyright 2020 Antti Poikela

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
