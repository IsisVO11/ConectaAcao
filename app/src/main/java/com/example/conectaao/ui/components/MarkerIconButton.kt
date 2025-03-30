package com.example.conectaao.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.conectaao.R
import com.example.conectaao.model.MarkerType

@Composable
fun MarkerIconButton(
    markerType: MarkerType,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    onClick: () -> Unit = {}
) {
    // Definir cores para cada tipo de marcador
    val (backgroundColor, iconResId) = when (markerType) {
        MarkerType.HOSPITAL -> Pair(Color(0xFFE57373), R.drawable.ic_hospital)
        MarkerType.POLICE -> Pair(Color(0xFF64B5F6), R.drawable.ic_police)
        MarkerType.FIRE_STATION -> Pair(Color(0xFFFFB74D), R.drawable.ic_fire)
        MarkerType.ACCESSIBLE_LOCATION -> Pair(Color(0xFF81C784), R.drawable.ic_accessible)
        MarkerType.VOLUNTEER -> Pair(Color(0xFFBA68C8), R.drawable.ic_volunteer)
        MarkerType.USER_LOCATION -> Pair(Color(0xFF4FC3F7), R.drawable.ic_user_location)
        MarkerType.HEALTH_CENTER -> Pair(Color(0xFFE57373), R.drawable.ic_hospital)
        MarkerType.POLICE_STATION -> Pair(Color(0xFF64B5F6), R.drawable.ic_police)
    }
    
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "Marcador de ${markerType.name}",
            tint = Color.White,
            modifier = Modifier.size(size * 0.6f)
        )
    }
}

/**
 * Função para criar um BitmapDescriptor a partir de um vetor de recursos
 */
fun bitmapDescriptorFromVector(
    context: android.content.Context,
    resourceId: Int
): com.google.android.gms.maps.model.BitmapDescriptor {
    val vectorDrawable = androidx.core.content.ContextCompat.getDrawable(context, resourceId)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    
    val bitmap = android.graphics.Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        android.graphics.Bitmap.Config.ARGB_8888
    )
    
    val canvas = android.graphics.Canvas(bitmap)
    vectorDrawable.draw(canvas)
    
    return com.google.android.gms.maps.model.BitmapDescriptorFactory.fromBitmap(bitmap)
} 