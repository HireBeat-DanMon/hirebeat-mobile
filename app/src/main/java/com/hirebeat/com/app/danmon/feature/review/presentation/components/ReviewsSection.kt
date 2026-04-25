package com.hirebeat.com.app.danmon.feature.review.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                Text(
                    text = "+ Escribir reseña",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        if (reviews.isEmpty()) {
            Text(
                text = "Aún no hay reseñas.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = Spacing.Small)
            )
        } else {
            reviews.forEach { review ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Spacing.Small),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(Spacing.Medium)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = review.reviewerName,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = GoldStar,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(Spacing.ExtraSmall))
                                Text(
                                    text = review.rating.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        Text(
                            text = review.comment,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = Spacing.ExtraSmall)
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