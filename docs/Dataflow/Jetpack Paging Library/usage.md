# Usage

## Overview

- Component package: `app.datasource.paging`
- DI integration: `app.di.datasource.ProvidesPagingSource`

The integration includes an `AppPagingSource` class located in `app.datasource.paging` to facilitate working with the Paging Library.

## Example

This example utilizes `AppRoomSource` to retrieve data from a Room database and load it using a `Pager` configured with the assistance of `AppPagingSource`.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val pagingSource: AppPagingSource,
    private val roomSource: AppRoomSource
) : BaseViewModel() {

    val usersStore = StoreObject<Flow<PagingData<User>>>()

    override fun doBind() {
        launchAsync("doBind") {
            val userDao = roomSource.userDao
            val pager = pagingSource.getPager { userDao.getAllPaginated() }
            usersStore.set(pager.flow.cachedIn(viewModelScope))
        }
    }

}

@Composable
fun TemplateScreen() {
    val viewModel: TemplateViewModel = provideHiltViewModel()
    val users = viewModel.usersStore.asStateValue()
    val items = users?.collectAsLazyPagingItems()
    FixedTopBarLazyColumnLayout {
        items(items?.itemCount ?: 0) {
            UserItem(items?.get(it))
        }
    }
}

@Composable
private fun UserItem(user: User?) {
    Text(text = user?.firstName.orEmpty())
}
```
