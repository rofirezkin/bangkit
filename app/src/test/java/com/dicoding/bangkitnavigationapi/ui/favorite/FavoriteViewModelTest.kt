import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.bangkitnavigationapi.data.UserRepository
import com.dicoding.bangkitnavigationapi.data.local.entity.FavouriteUserEntity
import com.dicoding.bangkitnavigationapi.ui.favorite.FavoriteViewModel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: FavoriteViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = FavoriteViewModel(userRepository)
    }

    @Test
    fun `test getUserDetail`() {
        // given
        val fakeUserData = listOf(FavouriteUserEntity("userId1", "John Doe"), FavouriteUserEntity("userId2", "Jane Doe"))
        val fakeLiveData = MutableLiveData<List<FavouriteUserEntity>>()
        fakeLiveData.value = fakeUserData
        `when`(userRepository.getAllData()).thenReturn(fakeLiveData)

        // when
        val observedData = viewModel.getUserDetail()

        // then
        Assert.assertEquals(fakeUserData, observedData.value)
    }
}
