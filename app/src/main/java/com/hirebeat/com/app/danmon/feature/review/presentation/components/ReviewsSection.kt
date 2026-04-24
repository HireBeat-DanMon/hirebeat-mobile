package com.hirebeat.com.app.danmon.feature.review.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hirebeat.com.app.danmon.core.theme.GoldStar
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review
import com.hirebeat.com.app.danmon.feature.review.presentation.screens.AddReviewModal

@Composable
fun ReviewsSection(
    reviews: List<Review>,
    ratingInput: Int,
    commentInput: String,
    onRatingChange: (Int) -> Unit,
    onCommentChange: (String) -> Unit,
    hasAlreadyReviewed: Boolean,
    showAddModal: Boolean,
    isSubmitting: Boolean,
    onToggleModal: (Boolean) -> Unit,
    onSubmitReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (!hasAlreadyReviewed) {
            TextButton(
                onClick = { onToggleModal(true) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("+ Escribir reseña", color = MaterialTheme.colorScheme.primary)
            }
        }

        if (reviews.isEmpty()) {
            Text(
                text = "Aún no hay reseñas.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = Spacing.Small)
            )
        } else {
            reviews.forEach { review ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Spacing.Small),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(Spacing.Medium)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(review.reviewerName, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = GoldStar,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(review.rating.toString(), fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(
                            text = review.comment,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        if (showAddModal) {
            AddReviewModal(
                rating = ratingInput,
                comment = commentInput,
                onRatingChange = onRatingChange,
                onCommentChange = onCommentChange,
                onDismiss = { onToggleModal(false) },
                onSubmit = onSubmitReview,
                isSubmitting = isSubmitting
            )
        }
    }
}