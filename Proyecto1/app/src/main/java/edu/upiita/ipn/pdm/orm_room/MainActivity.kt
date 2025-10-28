package edu.upiita.ipn.pdm.orm_room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import edu.upiita.ipn.pdm.orm_room.ui.theme.ORMRoomTheme
import edu.upiita.ipn.pdm.orm_room.model.AppDatabase
import edu.upiita.ipn.pdm.orm_room.model.UserRepository
import edu.upiita.ipn.pdm.orm_room.ui.AppScaffold
import edu.upiita.ipn.pdm.orm_room.ui.Navigator
import edu.upiita.ipn.pdm.orm_room.viewmodel.UserViewModel
import edu.upiita.ipn.pdm.orm_room.viewmodel.UserViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "users.db").fallbackToDestructiveMigration().build()
        val dao = db.usuarioDao()
        val taskDao = db.taskDao()
        val repo = UserRepository(dao, taskDao)
        val viewModelFactory = UserViewModelFactory(repo)

        setContent {
            ORMRoomTheme {
                val viewModel: UserViewModel = viewModel(factory = viewModelFactory)
                AppScaffold(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ORMRoomTheme {
        Greeting("Android")
    }
}
