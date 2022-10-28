package com.seboba.scratch.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.fragment
import androidx.navigation.navOptions

enum class NavType {
    XML_ROUTE, DSL_ROUTE, XML_ID;
    val routeBased get() = when(this) {
        XML_ROUTE -> true
        DSL_ROUTE -> true
        XML_ID -> false
    }
}
private val navType: NavType = NavType.XML_ID
private val routeBased: Boolean = navType.routeBased

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val nav = findNavController(R.id.nav_host_fragment)
        when(navType) {
            NavType.XML_ROUTE -> nav.setGraph(R.navigation.nav_route_based)
            NavType.DSL_ROUTE -> nav.setGraph(nav.createRouteGraph(), null)
            NavType.XML_ID ->  nav.setGraph(R.navigation.nav_id_based)
        }
    }
}

fun NavController.createRouteGraph(): NavGraph {
    return createGraph( startDestination = "home") {
        fragment<HomeFragment>("home")
        fragment<SplashFragment>("splash")
        fragment<ContentFragment>("content")
    }
}

class HomeFragment: ComposeFragment() {
    @Composable
    override fun Content() {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text("Home Fragment $navType")
            Button(onClick = {
                if (routeBased) {
                    nav.navigate(route = "splash")
                } else {
                    nav.navigate(R.id.splashFragment)
                }
            }) {
                Text("Go to splash fragment")
            }
        }
    }
}

class SplashFragment: ComposeFragment() {
    @Composable
    override fun Content() {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text("Splash Fragment")
            Button(onClick = {
                if (routeBased) {
                    nav.navigate(route = "content", navOptions { popUpTo(route = "splash") { inclusive = true } })
                } else {
                    nav.navigate(R.id.contentFragment, args = null, navOptions {
                        popUpTo(R.id.splashFragment) { inclusive = true }
                    })
                }
            }) {
                Text("Go to content fragment")
            }
            Button(onClick = { nav.popBackStack() }) {
                Text("Back")
            }
        }
    }
}

class ContentFragment: ComposeFragment() {
    @Composable
    override fun Content() {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text("Content Fragment")
            Button(onClick = { nav.popBackStack() }) {
                Text("Back")
            }
        }
    }
}
