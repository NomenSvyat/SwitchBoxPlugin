# SwitchBox Android plugin

If you add a lot of fields to BuildConfig class via gradle this plugin is for you.

Just add to your gradle script this block of code:

```
switchBox {
    filePath = "path-to-your-file"
}
```

Now create a file with JSON containing your fields like this:

```
{
  "prop1": {
    "default": true,
    "alpha": false
  },
  "prop2": {
    "default": "def",
    "alpha": "alpha",
    "debug": "non-def"
  },
  "prop3": {
    "default": 1,
    "debug": 2
  }
}
```

