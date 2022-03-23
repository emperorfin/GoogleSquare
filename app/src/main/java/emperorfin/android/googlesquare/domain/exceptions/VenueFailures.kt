package emperorfin.android.googlesquare.domain.exceptions

import emperorfin.android.googlesquare.domain.exceptions.Failure.FeatureFailure


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 08th March, 2022.
 */


sealed class VenueFailures {

    class ListNotAvailableMemoryVenueError(val cause: Throwable? = null) : FeatureFailure()
    class ListNotAvailableLocalVenueError(val cause: Throwable? = null) : FeatureFailure()
    class ListNotAvailableRemoteVenueError(val cause: Throwable? = null) : FeatureFailure()

    class MemoryVenueError(val cause: Throwable? = null) : FeatureFailure()
    class LocalVenueError(val cause: Throwable? = null) : FeatureFailure()
    class RemoteVenueError(val cause: Throwable? = null) : FeatureFailure()

    class MemoryGetVenueError(val cause: Throwable? = null) : FeatureFailure()
    class LocalGetVenueError(val cause: Throwable? = null) : FeatureFailure()
    class RemoteGetVenueError(val cause: Throwable? = null) : FeatureFailure()

    class MemoryInsertVenueError(val cause: Throwable? = null) : FeatureFailure()
    class LocalInsertVenueError(val cause: Throwable? = null) : FeatureFailure()
    class RemoteInsertVenueError(val cause: Throwable? = null) : FeatureFailure()

    class MemoryUpdateVenueError(val cause: Throwable? = null) : FeatureFailure()
    class LocalUpdateVenueError(val cause: Throwable? = null) : FeatureFailure()
    class RemoteUpdateVenueError(val cause: Throwable? = null) : FeatureFailure()

    class MemoryDeleteVenueError(val cause: Throwable? = null) : FeatureFailure()
    class LocalDeleteVenueError(val cause: Throwable? = null) : FeatureFailure()
    class RemoteDeleteVenueError(val cause: Throwable? = null) : FeatureFailure()

    class NonExistentDataMemoryVenueError(val cause: Throwable? = null) : FeatureFailure()
    class NonExistentDataLocalVenueError(val cause: Throwable? = null) : FeatureFailure()
    class NonExistentDataRemoteVenueError(val cause: Throwable? = null) : FeatureFailure()

    // For Repositories
    class RepositoryGetVenueError(val cause: Throwable? = null) : FeatureFailure()
}

