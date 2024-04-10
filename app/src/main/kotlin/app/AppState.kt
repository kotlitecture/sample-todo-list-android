package app

import core.ui.state.StoreState
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Represents the state of the application. This class extends [StoreState], providing functionality
 * to manage the application state using a store pattern.
 */
@Singleton
class AppState @Inject constructor() : StoreState()