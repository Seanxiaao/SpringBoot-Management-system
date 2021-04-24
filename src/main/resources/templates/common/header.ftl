<head>
    <meta charset="utf-8">
    <title>Management System</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap-fileinput/4.4.7/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
</head>

<style>
    body {
        position: relative;
        overflow-x: hidden;
    }
    body,
    html {
        height: 100%;
        /*background-color: #583e7e;*/
    }
    .nav .open > a {
        background-color: transparent;
    }
    .nav .open > a:hover {
        background-color: transparent;
    }
    .nav .open > a:focus {
        background-color: transparent;
    }
    /*-------------------------------*/
    /*           Wrappers            */
    /*-------------------------------*/
    #wrapper {
        -moz-transition: all 0.5s ease;
        -o-transition: all 0.5s ease;
        -webkit-transition: all 0.5s ease;
        padding-left: 0;
        transition: all 0.5s ease;
    }

    #wrapper.toggled {
        padding-left: 220px;
    }

    #wrapper.toggled #sidebar-wrapper {
        width: 220px;
    }

    #sidebar-wrapper {
        -moz-transition: all 0.5s ease;
        -o-transition: all 0.5s ease;
        -webkit-transition: all 0.5s ease;
        background: #1a1a1a;
        height: 100%;
        left: 220px;
        margin-left: -220px;
        overflow-x: hidden;
        overflow-y: auto;
        transition: all 0.5s ease;
        width: 0;
        z-index: 1000;
    }
    #sidebar-wrapper::-webkit-scrollbar {
        display: none;
    }

    #page-content-wrapper {
        padding-top: 70px;
        padding-left: 100px;
        width: 80%;
    }

    /*-------------------------------*/
    /*     Sidebar nav styles        */
    /*-------------------------------*/
    .sidebar-nav {
        list-style: none;
        margin: 0;
        padding: 0;
        position: absolute;
        top: 0;
        width: 220px;
    }
    .sidebar-nav li {
        display: inline-block;
        line-height: 20px;
        position: relative;
        width: 100%;
    }
    .sidebar-nav li:before {
        background-color: #1c1c1c;
        content: '';
        height: 100%;
        left: 0;
        position: absolute;
        top: 0;
        -webkit-transition: width 0.2s ease-in;
        transition: width 0.2s ease-in;
        width: 3px;
        z-index: -1;
    }
    .sidebar-nav li:first-child a {
        background-color: #1a1a1a;
        color: #ffffff;
    }
    .sidebar-nav li:nth-child(2):before {
        background-color: #402d5c;
    }
    .sidebar-nav li:nth-child(3):before {
        background-color: #4c366d;
    }
    .sidebar-nav li:nth-child(4):before {
        background-color: #583e7e;
    }
    .sidebar-nav li:nth-child(5):before {
        background-color: #64468f;
    }
    .sidebar-nav li:nth-child(6):before {
        background-color: #704fa0;
    }
    .sidebar-nav li:nth-child(7):before {
        background-color: #7c5aae;
    }
    .sidebar-nav li:nth-child(8):before {
        background-color: #8a6cb6;
    }
    .sidebar-nav li:nth-child(9):before {
        background-color: #987dbf;
    }
    .sidebar-nav li:hover:before {
        -webkit-transition: width 0.2s ease-in;
        transition: width 0.2s ease-in;
        width: 100%;
    }
    .sidebar-nav li a {
        color: #dddddd;
        display: block;
        padding: 10px 15px 10px 30px;
        text-decoration: none;
    }
    .sidebar-nav li.open:hover before {
        -webkit-transition: width 0.2s ease-in;
        transition: width 0.2s ease-in;
        width: 100%;
    }
    .sidebar-nav .dropdown-menu {
        background-color: #222222;
        border-radius: 0;
        border: none;
        box-shadow: none;
        margin: 0;
        padding: 0;
        position: relative;
        width: 100%;
    }
    .sidebar-nav li a:hover,
    .sidebar-nav li a:active,
    .sidebar-nav li a:focus,
    .sidebar-nav li.open a:hover,
    .sidebar-nav li.open a:active,
    .sidebar-nav li.open a:focus {
        background-color: transparent;
        color: #ffffff;
        text-decoration: none;
    }
    .sidebar-nav > .sidebar-brand {
        font-size: 18px;
        height: 65px;
        line-height: 36px;
    }
</style>