module.exports = function (grunt) {
    grunt.initConfig({
        less: {
            war: {
                files: [
                    {
                        cwd: 'less',
                        src: '**/*.less',
                        ext: '.css',
                        expand: true,
                        dest: '../kanban-app/target/kanban-app-1.0-SNAPSHOT/css'
                    }
                ]
            },
            dev: {
                files: [
                    {
                        cwd: 'less',
                        src: '**/*.less',
                        ext: '.css',
                        expand: true,
                        dest: 'css'
                    }
                ]
            }
        },
        newer: {
            options: {
                override: (function () {
                    var fs = require('fs');
                    var path = require('path');

                    function includeIfImportedFileChanged(lessFile, mTime, include) {
                        function fileChanged(file) {
                            return fs.existsSync(file) && fs.statSync(file).mtime > mTime;
                        }

                        function whenFileChanged(file, include, process) {
                            fs.readFile(file, "utf8", function (err, data) {
                                var lessDir = path.dirname(file),
                                    regex = /@import\s*(?:\(.*?\))?\s*"(.*?)(?:\.less)?"\s*;/g,
                                    result, imports = [];
                                while (result = regex.exec(data)) {
                                    // All of my less files are in the same directory,
                                    // other paths may need to be traversed for different setups...
                                    var baseName = result[1];
                                    var importFile = lessDir + '/' + baseName + '.less';
                                    imports.push(importFile);
                                    if (fileChanged(importFile)) {
                                        include(true);
                                        return;
                                    }
                                }
                                process(imports);
                            });
                        }

                        var files = [];
                        var processed = {lessFile: true};
                        whenFileChanged(lessFile, include, function (imports) {
                            files = files.concat(imports);
                            while (files.length) {
                                var filename = files.pop();
                                if (!processed[filename]) {
                                    processed[filename] = true;
                                    whenFileChanged(filename, include, arguments.callee);
                                    return;
                                }
                            }
                            include(false);
                        });
                    }

                    return function (details, include) {
                        if (details.task === 'less') {
                            includeIfImportedFileChanged(details.path, details.time, include);
                        } else {
                            include(true);
                        }
                    }
                })()
            }
        },
        watch: {
            war: {
                files: ['less/**/*.less'],
                tasks: ['newer:less:war']
            },
            dev: {
                files: ['less/**/*.less'],
                tasks: ['newer:less:dev']
            }
        }
    });
    require('load-grunt-tasks')(grunt);
};