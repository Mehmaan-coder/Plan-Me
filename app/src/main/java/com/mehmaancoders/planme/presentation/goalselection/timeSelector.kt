package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

// Time selection modes
enum class TimeSelectionMode {
    HOURS_PER_DAY,
    DAYS_PER_WEEK
}

// Theme colors matching the UI design
object TimeSelectorTheme {
    val screenBackground = Color(0xFFFDF7F3)
    val primaryText = Color(0xFF3B2B20)
    val secondaryText = Color(0xFF8B7D73)
    val selectedTimeBackground = Color(0xFF9CBF60)
    val unselectedTimeColor = Color(0xFFBBBBBB)
    val selectedTimeText = Color.White
    val deadlineTextColor = Color(0xFFFF6600)
    val buttonBackground = Color(0xFF3B2B20)
    val buttonContent = Color.White
    val surfaceVariant = Color(0xFFF5F5F5)
    val modeSelectorBackground = Color(0xFF9CBF60)
    val modeSelectorText = Color.White
}

// Data class for time options
@Stable
data class TimeOption(
    val value: Int,
    val displayText: String,
    val unit: String,
    val isRecommended: Boolean = false
) {
    companion object {
        fun createOptions(mode: TimeSelectionMode): List<TimeOption> {
            return when (mode) {
                TimeSelectionMode.HOURS_PER_DAY -> {
                    (1..24).map { hour ->
                        TimeOption(
                            value = hour,
                            displayText = hour.toString(),
                            unit = if (hour == 1) "Hour" else "Hours",
                            isRecommended = hour in 2..4
                        )
                    }
                }
                TimeSelectionMode.DAYS_PER_WEEK -> {
                    (1..7).map { day ->
                        TimeOption(
                            value = day,
                            displayText = day.toString(),
                            unit = if (day == 1) "Day" else "Days",
                            isRecommended = day in 3..5
                        )
                    }
                }
            }
        }
    }
}

@Stable
data class TimeSelectorState(
    val selectedTime: Int,
    val timeOptions: List<TimeOption>,
    val currentMode: TimeSelectionMode,
    val isLoading: Boolean = false,
    val error: String? = null,
    val progressStep: Int = 2,
    val totalSteps: Int = 5
) {
    val selectedOption: TimeOption?
        get() = timeOptions.find { it.value == selectedTime }

    val isValidSelection: Boolean
        get() = selectedOption != null
}

// ViewModel with proper state management
@Stable
class TimeSelectorViewModel(
    initialMode: TimeSelectionMode = TimeSelectionMode.HOURS_PER_DAY
) {
    private val _state = mutableStateOf(
        TimeSelectorState(
            selectedTime = 5, // Default to 5 hours/days to match the design
            timeOptions = TimeOption.createOptions(initialMode),
            currentMode = initialMode
        )
    )
    val state: State<TimeSelectorState> = _state

    fun updateSelectedTime(time: Int) {
        val currentState = _state.value
        val isValidTime = currentState.timeOptions.any { it.value == time }

        if (isValidTime) {
            _state.value = currentState.copy(
                selectedTime = time,
                error = null
            )
        }
    }

    fun switchMode(mode: TimeSelectionMode) {
        if (_state.value.currentMode != mode) {
            val newOptions = TimeOption.createOptions(mode)
            val defaultSelection = _state.value.selectedTime.coerceIn(1, newOptions.size)

            _state.value = _state.value.copy(
                currentMode = mode,
                timeOptions = newOptions,
                selectedTime = defaultSelection
            )
        }
    }
}

@Composable
fun rememberTimeSelectorViewModel(
    navHostController: NavHostController,
    initialMode: TimeSelectionMode = TimeSelectionMode.HOURS_PER_DAY
): TimeSelectorViewModel {
    return remember(initialMode) { TimeSelectorViewModel(initialMode = initialMode) }
}

// Constants for responsive design
private object TimeSelectorConstants {
    val ITEM_HEIGHT = 60.dp
    val SELECTED_ITEM_HEIGHT = 100.dp
    val MODE_CIRCLE_SIZE = 80.dp
    val ITEM_SPACING = 8.dp
    val PICKER_HEIGHT = 400.dp
    val CONTENT_PADDING = 160.dp
    const val VISIBILITY_THRESHOLD = 0.3f
}

// Enhanced time item with better visual feedback
@Composable
private fun TimeItem(
    timeOption: TimeOption,
    isSelected: Boolean,
    onClick: () -> Unit,
    scrollProgress: Float,
    modifier: Modifier = Modifier
) {
    val animatedSize by animateDpAsState(
        targetValue = if (isSelected) TimeSelectorConstants.SELECTED_ITEM_HEIGHT else TimeSelectorConstants.ITEM_HEIGHT,
        animationSpec = tween(300),
        label = "size_animation"
    )

    val animatedFontSize by animateFloatAsState(
        targetValue = if (isSelected) 48f else 32f,
        animationSpec = tween(300),
        label = "font_animation"
    )

    val alpha = if (isSelected) 1f else (1f - abs(scrollProgress)).coerceIn(TimeSelectorConstants.VISIBILITY_THRESHOLD, 0.6f)

    Box(
        modifier = modifier
            .height(TimeSelectorConstants.ITEM_HEIGHT)
            .fillMaxWidth()
            .graphicsLayer {
                this.alpha = alpha
            }
            .clickable(
                role = Role.Button,
                onClickLabel = "Select ${timeOption.displayText}"
            ) { onClick() }
            .semantics {
                selected = isSelected
            },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(animatedSize)
                    .background(
                        color = TimeSelectorTheme.selectedTimeBackground,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = timeOption.displayText,
                    fontSize = animatedFontSize.sp,
                    fontWeight = FontWeight.Bold,
                    color = TimeSelectorTheme.selectedTimeText
                )
            }
        } else {
            Text(
                text = timeOption.displayText,
                fontSize = animatedFontSize.sp,
                fontWeight = FontWeight.Bold,
                color = TimeSelectorTheme.unselectedTimeColor
            )
        }
    }
}

// Optimized scroll behavior
@Composable
private fun rememberOptimizedScrollBehavior(
    lazyListState: LazyListState,
    onSelectionChange: (Int) -> Unit,
    timeOptions: List<TimeOption>
) {
    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (!lazyListState.isScrollInProgress && timeOptions.isNotEmpty()) {
            val layoutInfo = lazyListState.layoutInfo
            val viewportSize = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
            val viewportCenter = layoutInfo.viewportStartOffset + viewportSize / 2

            val centerItem = layoutInfo.visibleItemsInfo.minByOrNull { itemInfo ->
                val itemCenter = itemInfo.offset + itemInfo.size / 2
                abs(itemCenter - viewportCenter)
            }

            centerItem?.let { item ->
                val selectedIndex = item.index
                if (selectedIndex in timeOptions.indices) {
                    onSelectionChange(timeOptions[selectedIndex].value)
                }
            }
        }
    }
}

// Time picker component with vertical scrolling
@Composable
private fun TimePicker(
    state: TimeSelectorState,
    onTimeSelected: (Int) -> Unit,
    onModeChanged: (TimeSelectionMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.selectedTime, state.timeOptions) {
        if (state.timeOptions.isNotEmpty()) {
            val initialIndex = state.timeOptions.indexOfFirst { it.value == state.selectedTime }
            if (initialIndex != -1 && !lazyListState.isScrollInProgress) {
                lazyListState.scrollToItem(initialIndex)
            }
        }
    }

    rememberOptimizedScrollBehavior(lazyListState, onTimeSelected, state.timeOptions)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Time picker column
        Box(
            modifier = Modifier
                .weight(0.1f)
                .height(TimeSelectorConstants.PICKER_HEIGHT),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = lazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(TimeSelectorConstants.ITEM_SPACING),
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(vertical = TimeSelectorConstants.CONTENT_PADDING),
                flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
            ) {
                itemsIndexed(
                    items = state.timeOptions,
                    key = { _, timeOption -> "${timeOption.value}_${timeOption.unit}" }
                ) { index, timeOption ->
                    val isSelected = timeOption.value == state.selectedTime

                    val layoutInfo = lazyListState.layoutInfo
                    val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == index }
                    val scrollProgress = itemInfo?.let { info ->
                        val viewportStart = layoutInfo.viewportStartOffset
                        val viewportEnd = layoutInfo.viewportEndOffset
                        val viewportCenter = viewportStart + (viewportEnd - viewportStart) / 2
                        val itemCenter = info.offset + info.size / 2
                        val distance = abs(itemCenter - viewportCenter).toFloat()
                        val maxDistance = (viewportEnd - viewportStart) / 2f

                        if (maxDistance > 0) {
                            (distance / maxDistance).coerceIn(0f, 1f)
                        } else {
                            1f
                        }
                    } ?: 1f

                    TimeItem(
                        timeOption = timeOption,
                        isSelected = isSelected,
                        onClick = {
                            onTimeSelected(timeOption.value)
                            coroutineScope.launch {
                                lazyListState.animateScrollToItem(index)
                            }
                        },
                        scrollProgress = scrollProgress
                    )
                }
            }
        }

        // Mode selector buttons on the right
        Column(
            modifier = Modifier.padding(end = 65.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Hours/Day button
            val isHoursSelected = state.currentMode == TimeSelectionMode.HOURS_PER_DAY
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        color = if (isHoursSelected) TimeSelectorTheme.modeSelectorBackground else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(
                        width = if (isHoursSelected) 0.dp else 1.5.dp,
                        color = if (isHoursSelected) Color.Transparent else TimeSelectorTheme.secondaryText,
                        shape = CircleShape
                    )
                    .clickable {
                        onModeChanged(TimeSelectionMode.HOURS_PER_DAY)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Hours/",
                        fontSize = 12.sp,
                        color = if (isHoursSelected) TimeSelectorTheme.modeSelectorText else TimeSelectorTheme.secondaryText,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 14.sp
                    )
                    Text(
                        text = "Day",
                        fontSize = 12.sp,
                        color = if (isHoursSelected) TimeSelectorTheme.modeSelectorText else TimeSelectorTheme.secondaryText,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 14.sp
                    )
                }
            }

            // Days/Week button
            val isDaysSelected = state.currentMode == TimeSelectionMode.DAYS_PER_WEEK
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        color = if (isDaysSelected) TimeSelectorTheme.modeSelectorBackground else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(
                        width = if (isDaysSelected) 0.dp else 1.5.dp,
                        color = if (isDaysSelected) Color.Transparent else TimeSelectorTheme.secondaryText,
                        shape = CircleShape
                    )
                    .clickable {
                        onModeChanged(TimeSelectionMode.DAYS_PER_WEEK)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Days/",
                        fontSize = 12.sp,
                        color = if (isDaysSelected) TimeSelectorTheme.modeSelectorText else TimeSelectorTheme.secondaryText,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 14.sp
                    )
                    Text(
                        text = "Week",
                        fontSize = 12.sp,
                        color = if (isDaysSelected) TimeSelectorTheme.modeSelectorText else TimeSelectorTheme.secondaryText,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 14.sp
                    )
                }
            }
        }
    }
}

// Main time selector screen with navigation
@Composable
fun TimeSelectorScreen(
    navController: NavHostController,
    initialMode: TimeSelectionMode = TimeSelectionMode.HOURS_PER_DAY,
    modifier: Modifier = Modifier
) {
    val viewModel = rememberTimeSelectorViewModel(
        navHostController = navController,
        initialMode = initialMode
    )
    val state by viewModel.state

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TimeSelectorTheme.screenBackground)
            .statusBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    // Navigate back to GoalSelectionScreen
                    navController.navigate(Routes.GoalSelectionScreen)
                },
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = 1.5.dp,
                        color = TimeSelectorTheme.primaryText,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.planme_back),
                    contentDescription = "Back",
                    tint = TimeSelectorTheme.primaryText,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Default Parameters",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = TimeSelectorTheme.primaryText
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${state.progressStep} of ${state.totalSteps}",
                fontSize = 14.sp,
                color = TimeSelectorTheme.secondaryText,
                modifier = Modifier
                    .background(
                        color = TimeSelectorTheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title
        Text(
            text = "How much time can you\ndedicate to this goal?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TimeSelectorTheme.primaryText,
            textAlign = TextAlign.Center,
            lineHeight = 34.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Time picker with mode selector
        TimePicker(
            state = state,
            onTimeSelected = { viewModel.updateSelectedTime(it) },
            onModeChanged = { viewModel.switchMode(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Deadline text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Short on time? ",
                fontSize = 14.sp,
                color = TimeSelectorTheme.secondaryText
            )
            Text(
                text = "Set a deadline.",
                fontSize = 14.sp,
                color = TimeSelectorTheme.deadlineTextColor,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Continue button
        Button(
            onClick = {
                navController.navigate(Routes.BudgetSelectorScreen)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = TimeSelectorTheme.buttonBackground
            ),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TimeSelectorTheme.buttonContent
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = TimeSelectorTheme.buttonContent,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// Overloaded version for backward compatibility (can be removed if not needed)
@Composable
fun TimeSelectorScreen(
    viewModel: TimeSelectorViewModel = TimeSelectorViewModel(),
    onContinue: (Int, TimeSelectionMode) -> Unit = { _, _ -> },
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.state

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TimeSelectorTheme.screenBackground)
            .statusBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = 1.5.dp,
                        color = TimeSelectorTheme.primaryText,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.planme_back),
                    contentDescription = "Back",
                    tint = TimeSelectorTheme.primaryText,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Default Parameters",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = TimeSelectorTheme.primaryText
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${state.progressStep} of ${state.totalSteps}",
                fontSize = 14.sp,
                color = TimeSelectorTheme.secondaryText,
                modifier = Modifier
                    .background(
                        color = TimeSelectorTheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title
        Text(
            text = "How much time can you\ndedicate to this goal?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TimeSelectorTheme.primaryText,
            textAlign = TextAlign.Center,
            lineHeight = 34.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Time picker with mode selector
        TimePicker(
            state = state,
            onTimeSelected = { viewModel.updateSelectedTime(it) },
            onModeChanged = { viewModel.switchMode(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Deadline text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Short on time? ",
                fontSize = 14.sp,
                color = TimeSelectorTheme.secondaryText
            )
            Text(
                text = "Set a deadline.",
                fontSize = 14.sp,
                color = TimeSelectorTheme.deadlineTextColor,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Continue button
        Button(
            onClick = { onContinue(state.selectedTime, state.currentMode) },
            colors = ButtonDefaults.buttonColors(
                containerColor = TimeSelectorTheme.buttonBackground
            ),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TimeSelectorTheme.buttonContent
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = TimeSelectorTheme.buttonContent,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TimeSelectorScreenPreview() {
    // For preview, we'll use the overloaded version without navigation
    TimeSelectorScreen()
}