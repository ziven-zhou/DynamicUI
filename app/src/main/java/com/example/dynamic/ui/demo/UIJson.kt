package com.example.dynamic.ui.demo

val uiList = listOf(ROUTE_JSON, ROOT_JSON, ITEM_JSON)

const val ROUTE_JSON = """
    {
        "componentType": "Root",
        "componentTemp": true,
        "children": [
            {
                "componentType": "Scaffold",
                "routeName": "Main"
            },
            {
                "componentType": "Scaffold",
                "routeName": "Second"
            },
            {
                "componentType": "Scaffold",
                "routeName": "Third",
                "routeParams": [
                    "param1",
                    "param2"
                ]
            }
        ]
    }
"""

const val ROOT_JSON = """
    {
        "componentType": "Scaffold",
        "componentTemp": true,
        "componentId": "Scaffold",
        "componentName": "Scaffold",
        "layout": {
            "width": -1,
            "height": -1
        },
        "children": [
            {
                "componentName": "TopBar",
                "layout": {
                    "height": -2,
                    "width": -1
                },
                "value": {
                    "text": "Dynamic UI"
                },
                "style": {
                    "fontSize": 24,
                    "fontColor": "#000000"
                }
            },
            {
                "componentName": "BottomBar",
                    "layout": {
                    "height": -2,
                    "width": -2
                },
                "children": [
                    {
                        "componentName": "IconButton",
                        "layout": {
                            "height": -2,
                            "width": -2
                        },
                        "value": {
                            "image": "Menu",
                            "click": [
                                {
                                    "action": "Compose",
                                    "tryFirst": true,
                                    "routeName": "Main"
                                }
                            ]
                        }
                    },
                    {
                        "componentName": "IconButton",
                        "layout": {
                            "height": -2,
                            "width": -2
                        },
                        "value": {
                            "image": "Add",
                            "click": [
                                {
                                    "action": "Compose",
                                    "tryFirst": true,
                                    "routeName": "Second"
                                }
                            ]
                        }
                    },
                    {
                        "componentName": "IconButton",
                        "layout": {
                            "height": -2,
                            "width": -2
                        },
                        "value": {
                            "image": "Close",
                            "click": [
                                {
                                    "action": "Compose",
                                    "tryFirst": true,
                                    "routeName": "Third",
                                    "routeParams": [
                                        "value1",
                                        "value2"
                                    ]
                                }
                            ]
                        }
                    }
                ]
            },
            {
                "componentName": "SnackBar"
            },
            {
                "componentName": "FloatingActionButton",
                "layout": {
                    "height": -2,
                    "width": -2
                },
                "children": [
                    {
                        "componentName": "IconButton",
                        "value": {
                            "image": "Add"
                        }
                    }
                ]
            },
            {
               "componentName": "LazyColumn",
               "layout": {
                   "height": -1,
                   "width": -1
               },
               "children": [
                    {
                        "componentName": "Other",
                        "componentType": "item"
                    }
               ]
            }
        ]
    }
"""

const val ITEM_JSON = """
    {
        "componentType": "item",
        "componentTemp": true,
        "componentId": "item",
        "componentName": "Row",
        "layout": {
            "width": -1,
            "height": 160,
            "start": 8,
            "end": 8,
            "top": 4,
            "bottom": 4
        },
        "style": {
            "backgroundColor": "#FFEFB8C8",
            "topLeft": 16,
            "topRight": 16,
            "bottomLeft": 16,
            "bottomRight": 16
        },
        "children": [
            {
                "componentId": "itemButton",
                "componentName": "Button",
                "layout": {
                    "height":48,
                    "width": 120,
                    "start": 8,
                    "contentStart": 8,
                    "contentEnd": 8
                },
                "style": {
                    "fontSize": 12,
                    "fontColor": "#FFFFFF",
                    "align": "TopCenter",
                    "maxLines": 1,
                    "overflow": "Ellipsis",
                    "topLeft": 24,
                    "topRight": 24,
                    "bottomLeft": 24,
                    "bottomRight": 24
                },
                "value": {
                    "click": [
                        {
                            "content": "I am a SnackBar.",
                            "action": "SnackBar",
                            "tryFirst": true
                        }
                    ]
                }
            },
            {
                "componentId": "itemImage",
                "componentName": "Image",
                "layout": {
                    "height": -1,
                    "width": -1,
                    "start": 8
                },
                "style": {
                    "scale": "Crop",
                    "quality": "High",
                    "topLeft": 16,
                    "topRight": 16,
                    "bottomLeft": 16,
                    "bottomRight": 16
                },
                "value": {
                    "clickable": true
                    "click": [
                        {
                            "content": "I am a Activity.",
                            "action": "Activity",
                            "tryFirst": true
                        }
                    ]
                }
            }
        ]
    }
"""
