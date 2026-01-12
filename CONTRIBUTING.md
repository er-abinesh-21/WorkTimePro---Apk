# Contributing to WorkTime Pro

Thank you for your interest in contributing to WorkTime Pro! This document provides guidelines and instructions for contributing to the project.

---

## 🎯 Ways to Contribute

There are many ways you can contribute to WorkTime Pro:

1. **Report Bugs** - Help us identify and fix issues
2. **Suggest Features** - Share ideas for improvements
3. **Improve Documentation** - Enhance guides and documentation
4. **Submit Code** - Fix bugs or add new features
5. **Review Code** - Review pull requests from others
6. **Test** - Test the app on different devices and Android versions

---

## 🐛 Reporting Bugs

### Before Reporting
- Check if the bug has already been reported in existing issues
- Ensure you're using the latest version of the app
- Try to reproduce the bug consistently

### Bug Report Template

```markdown
**Bug Description**
A clear and concise description of the bug.

**Steps to Reproduce**
1. Go to '...'
2. Click on '...'
3. Enter '...'
4. See error

**Expected Behavior**
What you expected to happen.

**Actual Behavior**
What actually happened.

**Screenshots**
If applicable, add screenshots.

**Environment**
- Device: [e.g., Pixel 6]
- Android Version: [e.g., Android 13]
- App Version: [e.g., 1.0.0]

**Logcat Output**
```
Paste relevant Logcat output here
```

**Additional Context**
Any other information about the problem.
```

---

## 💡 Suggesting Features

### Feature Request Template

```markdown
**Feature Description**
A clear description of the feature you'd like to see.

**Problem it Solves**
What problem does this feature address?

**Proposed Solution**
How do you envision this feature working?

**Alternatives Considered**
What alternative solutions have you considered?

**Use Cases**
When/how would users use this feature?

**Priority**
How important is this feature to you?
- [ ] Critical
- [ ] High
- [ ] Medium
- [ ] Low

**Additional Context**
Mockups, examples, or references.
```

---

## 🔧 Code Contributions

### Development Setup

1. **Fork the Repository**
   ```bash
   # Fork via GitHub UI, then clone
   git clone https://github.com/YOUR_USERNAME/WorkTimePro.git
   cd WorkTimePro
   ```

2. **Open in Android Studio**
   - File → Open → Select WorkTimePro folder
   - Wait for Gradle sync

3. **Create a Branch**
   ```bash
   git checkout -b feature/your-feature-name
   # or
   git checkout -b fix/your-bug-fix
   ```

### Coding Standards

#### Kotlin Style Guide

Follow the [official Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html):

```kotlin
// ✅ Good
class WorkTimeCalculator {
    private val hoursPerDay = 8
    
    fun calculateLogoutTime(
        inTime: String,
        breakDuration: Int
    ): String {
        // Implementation
    }
}

// ❌ Bad
class worktimecalculator{
    private val Hours_Per_Day=8
    
    fun CalculateLogoutTime(in_time:String,break_duration:Int):String{
        //Implementation
    }
}
```

#### XML Style Guide

```xml
<!-- ✅ Good -->
<TextView
    android:id="@+id/tvLogoutTime"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/logout_time"
    android:textSize="16sp"
    android:textColor="?attr/colorPrimary" />

<!-- ❌ Bad -->
<TextView android:id="@+id/textview1" android:layout_width="wrap_content" 
    android:layout_height="wrap_content" android:text="Logout Time" 
    android:textSize="16sp"/>
```

#### Code Quality Rules

1. **No Hardcoded Strings**
   ```kotlin
   // ✅ Good
   textView.text = getString(R.string.logout_time)
   
   // ❌ Bad
   textView.text = "Logout Time"
   ```

2. **Proper Resource Naming**
   ```xml
   <!-- ✅ Good -->
   <string name="btn_calculate">Calculate</string>
   <color name="md_theme_light_primary">#1976D2</color>
   
   <!-- ❌ Bad -->
   <string name="text1">Calculate</string>
   <color name="blue">#1976D2</color>
   ```

3. **Comprehensive Comments**
   ```kotlin
   /**
    * Calculate logout time based on in-time and break duration
    * 
    * @param inTime Office in-time in HH:mm format
    * @param breakDuration Break duration in minutes
    * @return Logout time in HH:mm:ss AM/PM format
    */
   fun calculateLogoutTime(inTime: String, breakDuration: Int): String {
       // Implementation
   }
   ```

4. **Error Handling**
   ```kotlin
   // ✅ Good
   try {
       val result = performCalculation()
       displayResult(result)
   } catch (e: Exception) {
       Log.e(TAG, "Calculation failed", e)
       showError(getString(R.string.error_calculation))
   }
   
   // ❌ Bad
   val result = performCalculation()
   displayResult(result)
   ```

### Commit Guidelines

#### Commit Message Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

**Examples:**

```bash
# Feature
git commit -m "feat(calculator): add custom work hour input"

# Bug fix
git commit -m "fix(notification): resolve alarm not triggering issue"

# Documentation
git commit -m "docs(readme): update build instructions"

# Refactoring
git commit -m "refactor(database): improve query performance"
```

### Pull Request Process

1. **Ensure Quality**
   - Code builds without errors
   - No lint warnings
   - All features tested
   - Documentation updated

2. **Create Pull Request**
   ```markdown
   **Description**
   Brief description of changes.
   
   **Type of Change**
   - [ ] Bug fix
   - [ ] New feature
   - [ ] Documentation update
   - [ ] Code refactoring
   
   **Testing**
   - [ ] Tested on emulator
   - [ ] Tested on real device
   - [ ] All features work correctly
   - [ ] No crashes or errors
   
   **Screenshots**
   If UI changes, add screenshots.
   
   **Checklist**
   - [ ] Code follows style guidelines
   - [ ] Comments added for complex logic
   - [ ] Documentation updated
   - [ ] No hardcoded strings
   - [ ] Tested on multiple Android versions
   ```

3. **Review Process**
   - Wait for code review
   - Address feedback promptly
   - Make requested changes
   - Re-request review after updates

4. **Merge**
   - Pull request approved
   - All checks pass
   - Branch merged by maintainer

---

## 📝 Documentation Contributions

### Documentation Guidelines

1. **Use Clear Language**
   - Write for developers of all skill levels
   - Avoid jargon when possible
   - Provide examples

2. **Keep it Updated**
   - Update docs when code changes
   - Remove outdated information
   - Add version information

3. **Format Properly**
   - Use Markdown formatting
   - Add code blocks with syntax highlighting
   - Include screenshots/diagrams

4. **Check Spelling**
   - Run spell check
   - Use proper grammar
   - Maintain consistent terminology

### Documentation Files

| File | Purpose |
|------|---------|
| README.md | Project overview |
| BUILD_GUIDE.md | Build instructions |
| FEATURES.md | Feature documentation |
| QUICK_REFERENCE.md | Quick tips |
| CHANGELOG.md | Version history |
| CONTRIBUTING.md | This file |

---

## 🧪 Testing Guidelines

### Testing Checklist

Before submitting code:

- [ ] App builds successfully
- [ ] No compiler warnings
- [ ] No lint errors (or justified)
- [ ] Tested on emulator
- [ ] Tested on real device
- [ ] Tested in light mode
- [ ] Tested in dark mode
- [ ] Tested on different screen sizes
- [ ] Tested on different Android versions
- [ ] All features work as expected
- [ ] No performance regressions
- [ ] No memory leaks

### Test Cases

When adding features, test:

1. **Happy Path** - Normal usage
2. **Edge Cases** - Boundary conditions
3. **Error Cases** - Invalid inputs
4. **Performance** - Large datasets
5. **Compatibility** - Different devices/versions

---

## 🎨 UI/UX Guidelines

### Material Design 3

Follow Material Design 3 guidelines:

1. **Colors**
   - Use theme colors, not hardcoded
   - Maintain contrast ratios (WCAG AA)
   - Support light and dark themes

2. **Typography**
   - Use defined text styles
   - Maintain hierarchy
   - Ensure readability

3. **Spacing**
   - Use 8dp grid system
   - Consistent padding/margins
   - Proper touch targets (48dp min)

4. **Components**
   - Use Material components
   - Follow component guidelines
   - Maintain consistency

---

## 🔍 Code Review Checklist

### For Reviewers

When reviewing code:

- [ ] Code follows style guidelines
- [ ] Logic is clear and correct
- [ ] Error handling is proper
- [ ] Comments explain complex logic
- [ ] No hardcoded values
- [ ] Resources properly named
- [ ] No memory leaks
- [ ] Performance is acceptable
- [ ] Tests are adequate
- [ ] Documentation is updated

### For Contributors

Before requesting review:

- [ ] Self-review completed
- [ ] Code is clean and readable
- [ ] All tests pass
- [ ] Documentation updated
- [ ] Commit messages are clear
- [ ] Branch is up to date

---

## 🚀 Release Process

### Version Numbering

We use [Semantic Versioning](https://semver.org/):

- **Major** (X.0.0): Breaking changes
- **Minor** (1.X.0): New features, backwards compatible
- **Patch** (1.0.X): Bug fixes, backwards compatible

### Pre-Release Checklist

- [ ] All features complete
- [ ] All bugs fixed
- [ ] Documentation updated
- [ ] CHANGELOG updated
- [ ] Version number incremented
- [ ] Release notes prepared
- [ ] APK tested thoroughly
- [ ] Build configuration verified

---

## 📧 Communication

### Asking Questions

Feel free to ask questions:

1. Check existing documentation first
2. Search for similar issues
3. Provide context and details
4. Be respectful and patient

### Discussions

For discussions about:

- Architecture decisions
- Major feature proposals
- Breaking changes
- Project direction

---

## 🏆 Recognition

### Contributors

All contributors will be:

- Acknowledged in release notes
- Listed in CONTRIBUTORS.md
- Credited for their work

### Types of Contributions

We value all contributions:

- 🐛 Bug reports
- 💡 Feature suggestions
- 📝 Documentation
- 💻 Code
- 🧪 Testing
- 👀 Code reviews

---

## ⚖️ Code of Conduct

### Our Pledge

We pledge to make participation in our project a harassment-free experience for everyone.

### Our Standards

**Positive behaviors:**
- Being respectful
- Accepting feedback gracefully
- Focusing on what's best for the project
- Showing empathy

**Unacceptable behaviors:**
- Harassment or discrimination
- Trolling or insulting comments
- Personal or political attacks
- Publishing others' private information

### Enforcement

Violations may result in:
1. Warning
2. Temporary ban
3. Permanent ban

---

## 📚 Resources

### Learning Resources

- [Android Developer Docs](https://developer.android.com)
- [Kotlin Documentation](https://kotlinlang.org/docs)
- [Material Design Guidelines](https://material.io/design)
- [Git Best Practices](https://git-scm.com/book/en/v2)

### Project Resources

- [README.md](README.md)
- [BUILD_GUIDE.md](BUILD_GUIDE.md)
- [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)
- [QUICK_REFERENCE.md](QUICK_REFERENCE.md)

---

## 🙏 Thank You!

Thank you for contributing to WorkTime Pro! Your efforts help make this app better for everyone.

**Questions?** Don't hesitate to ask!

---

*Last Updated: January 9, 2026*
*Project: WorkTime Pro v1.0.0*
